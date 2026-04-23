package com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bashkevich.quizchecker.model.blank_template.domain.AnswerTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.SlotTemplate
import com.bashkevich.quizchecker.resources.Res
import com.bashkevich.quizchecker.resources.blank_template_add_button
import com.bashkevich.quizchecker.resources.blank_template_add_new_blank
import com.bashkevich.quizchecker.resources.blank_template_answers_label
import com.bashkevich.quizchecker.resources.blank_template_answers_loading_error
import com.bashkevich.quizchecker.resources.blank_template_loading_error
import com.bashkevich.quizchecker.resources.blank_template_points_label
import com.bashkevich.quizchecker.resources.blank_template_round_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuizDetailsBlanksScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizDetailsBlanksViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.actions) {
        viewModel.actions.collect { action ->
            when (action) {
                is QuizDetailsBlanksAction.ShowAddBlankError -> {
                    snackbarHostState.showSnackbar(message = action.message)
                }
            }
        }
    }

    Box(modifier = modifier) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.blank_template_loading_error),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> {
                BlankTemplatesPager(
                    modifier = Modifier.fillMaxSize(),
                    blankTemplates = state.blankTemplates,
                    slotAnswersStates = state.slotAnswersStates,
                    newBlankText = state.newBlankText,
                    isAddingBlank = state.isAddingBlank,
                    onLoadSlotAnswers = { slotId -> viewModel.onEvent(QuizDetailsBlanksEvent.LoadSlotAnswers(slotId)) },
                    onNewBlankTextChanged = { text -> viewModel.onEvent(QuizDetailsBlanksEvent.OnNewBlankTextChanged(text)) },
                    onAddBlankTemplate = { viewModel.onEvent(QuizDetailsBlanksEvent.AddBlankTemplate) }
                )
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BlankTemplatesPager(
    modifier: Modifier = Modifier,
    blankTemplates: List<BlankTemplate>,
    slotAnswersStates: Map<Int, SlotAnswersState>,
    newBlankText: String,
    isAddingBlank: Boolean,
    onLoadSlotAnswers: (Int) -> Unit,
    onNewBlankTextChanged: (String) -> Unit,
    onAddBlankTemplate: () -> Unit
) {
    val pageCount = blankTemplates.size + 1
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )
    val isOnAddPage = pagerState.currentPage == blankTemplates.size

    // Reposition to newly added blank when list grows
    LaunchedEffect(blankTemplates.size) {
        if (blankTemplates.isNotEmpty()) {
            pagerState.scrollToPage(blankTemplates.size - 1)
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.then(modifier),
        contentPadding = PaddingValues(horizontal = 16.dp),
        pageSpacing = 16.dp
    ) { pageIndex ->
        if (pageIndex < blankTemplates.size) {
            val template = blankTemplates[pageIndex]
            BlankTemplatePage(
                blankTemplate = template,
                slotAnswersStates = slotAnswersStates,
                onLoadSlotAnswers = onLoadSlotAnswers,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            AddBlankTemplatePage(
                blankText = newBlankText,
                onBlankTextChanged = onNewBlankTextChanged,
                isOnAddPage = isOnAddPage,
                isAddingBlank = isAddingBlank,
                onAddClick = onAddBlankTemplate,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun AddBlankTemplatePage(
    modifier: Modifier = Modifier,
    blankText: String,
    onBlankTextChanged: (String) -> Unit,
    isOnAddPage: Boolean,
    isAddingBlank: Boolean,
    onAddClick: () -> Unit
) {
    var isFormVisible by rememberSaveable { mutableStateOf(blankText.isNotEmpty()) }

    LaunchedEffect(isOnAddPage) {
        if (!isOnAddPage && blankText.isEmpty()) isFormVisible = false
    }

    Column(
        modifier = Modifier.then(modifier).padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = isFormVisible) {
            Text(
                text = stringResource(Res.string.blank_template_add_new_blank),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (!isFormVisible) {
            Button(onClick = { isFormVisible = true }) {
                Text(text = stringResource(Res.string.blank_template_add_new_blank))
            }
        }

        AnimatedVisibility(
            visible = isFormVisible,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = blankText,
                    onValueChange = onBlankTextChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    placeholder = {
                        Text(text = stringResource(Res.string.blank_template_add_new_blank))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onAddClick,
                    enabled = blankText.isNotBlank() && !isAddingBlank,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    if (isAddingBlank) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(text = stringResource(Res.string.blank_template_add_button))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun BlankTemplatePage(
    modifier: Modifier = Modifier,
    blankTemplate: BlankTemplate,
    slotAnswersStates: Map<Int, SlotAnswersState>,
    onLoadSlotAnswers: (Int) -> Unit
) {
    val roundTitle = stringResource(
        Res.string.blank_template_round_title,
        blankTemplate.roundNumber,
        blankTemplate.title
    )

    Column(
        modifier = Modifier.then(modifier).padding(vertical = 8.dp)
    ) {
        Text(
            text = roundTitle,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = blankTemplate.slots,
                key = { it.id }
            ) { slot ->
                SlotItem(
                    slot = slot,
                    answersState = slotAnswersStates[slot.id],
                    onLoadAnswers = { onLoadSlotAnswers(slot.id) }
                )
            }
        }
    }
}

@Composable
private fun SlotItem(
    modifier: Modifier = Modifier,
    slot: SlotTemplate,
    answersState: SlotAnswersState?,
    onLoadAnswers: () -> Unit
) {
    val hasMultipleAnswers = slot.answersAmount > 1
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(4.dp)
                )
                .then(
                    if (hasMultipleAnswers) Modifier.clickable {
                        isExpanded = !isExpanded
                        if (isExpanded && answersState == null) {
                            onLoadAnswers()
                        }
                    } else Modifier
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${slot.slotNumber}.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = if (hasMultipleAnswers) {
                    stringResource(Res.string.blank_template_answers_label, slot.answersAmount)
                } else {
                    slot.answer?.answer ?: ""
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            if (hasMultipleAnswers) {
                Text(
                    text = if (isExpanded) "▲" else "▼",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        AnimatedVisibility(
            visible = isExpanded && hasMultipleAnswers,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            SlotAnswersContent(answersState = answersState)
        }
    }
}

@Composable
private fun SlotAnswersContent(
    modifier: Modifier = Modifier,
    answersState: SlotAnswersState?
) {
    when (answersState) {
        is SlotAnswersState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
        }
        is SlotAnswersState.Error -> {
            Text(
                text = stringResource(Res.string.blank_template_answers_loading_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = modifier.padding(top = 8.dp, start = 12.dp)
            )
        }
        is SlotAnswersState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                answersState.answers.forEach { answer ->
                    AnswerItem(answer = answer)
                }
            }
        }
        null, is SlotAnswersState.Idle -> {
            // Initial state — should trigger load
        }
    }
}

@Composable
private fun AnswerItem(
    modifier: Modifier = Modifier,
    answer: AnswerTemplate
) {
    val pointsText = stringResource(Res.string.blank_template_points_label, answer.points)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
    ) {
        Text(
            text = answer.answer,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = pointsText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
