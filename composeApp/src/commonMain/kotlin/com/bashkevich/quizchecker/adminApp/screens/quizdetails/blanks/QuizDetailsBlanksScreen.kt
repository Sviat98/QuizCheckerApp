package com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.SlotTemplate
import com.bashkevich.quizchecker.resources.Res
import com.bashkevich.quizchecker.resources.blank_template_answers_label
import com.bashkevich.quizchecker.resources.blank_template_loading_error
import com.bashkevich.quizchecker.resources.blank_template_no_templates
import com.bashkevich.quizchecker.resources.blank_template_round_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuizDetailsBlanksScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizDetailsBlanksViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.blank_template_loading_error),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        state.blankTemplates.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.blank_template_no_templates),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        else -> {
            BlankTemplatesPager(
                modifier = modifier,
                blankTemplates = state.blankTemplates
            )
        }
    }
}

@Composable
private fun BlankTemplatesPager(
    modifier: Modifier = Modifier,
    blankTemplates: List<BlankTemplate>
) {
    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { Int.MAX_VALUE }
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.then(modifier),
        contentPadding = PaddingValues(horizontal = 16.dp),
        pageSpacing = 16.dp
    ) { pageIndex ->
        val template = blankTemplates[pageIndex % blankTemplates.size]
        BlankTemplatePage(
            blankTemplate = template,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun BlankTemplatePage(
    modifier: Modifier = Modifier,
    blankTemplate: BlankTemplate
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
                SlotItem(slot = slot)
            }
        }
    }
}

@Composable
private fun SlotItem(
    modifier: Modifier = Modifier,
    slot: SlotTemplate
) {
    val slotText = if (slot.answersAmount > 1) {
        stringResource(Res.string.blank_template_answers_label, slot.answersAmount)
    } else {
        slot.answer?.answer ?: ""
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = "${slot.slotNumber}.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = slotText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
