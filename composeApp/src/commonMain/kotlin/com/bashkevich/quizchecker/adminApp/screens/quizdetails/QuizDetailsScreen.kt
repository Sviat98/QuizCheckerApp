package com.bashkevich.quizchecker.adminApp.screens.quizdetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bashkevich.quizchecker.adminApp.LocalNavHostController
import com.bashkevich.quizchecker.adminApp.SettingsRoute
import com.bashkevich.quizchecker.components.icons.IconGroup
import com.bashkevich.quizchecker.components.icons.default_icons.ArrowBack
import com.bashkevich.quizchecker.core.convertFromEnum
import com.bashkevich.quizchecker.core.convertToLocalTimeFromUTC
import com.bashkevich.quizchecker.model.Status
import com.bashkevich.quizchecker.model.quiz.domain.Quiz

@Composable
fun QuizDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizDetailsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navController = LocalNavHostController.current

    when {
        state.isLoading -> {
            QuizDetailsLoading(modifier = modifier)
        }
        else -> {
            QuizDetailsContent(
                modifier = modifier,
                state = state,
                onEvent = { viewModel.onEvent(it) },
                onBack = { navController.navigateUp() },
                onNavigateToSettings = {navController.navigate(SettingsRoute)}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizDetailsContent(
    modifier: Modifier = Modifier,
    state: QuizDetailsScreenState,
    onEvent: (QuizDetailsScreenUiEvent) -> Unit,
    onBack: () -> Unit,
    onNavigateToSettings: ()->Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = IconGroup.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            QuizDetailsInfo(
                quiz = state.quiz,
                onEvent = onEvent,
                onNavigateToSettings = onNavigateToSettings
            )
        }
    }
}

@Composable
private fun QuizDetailsInfo(
    quiz: Quiz,
    onEvent: (QuizDetailsScreenUiEvent) -> Unit,
    onNavigateToSettings: ()->Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = quiz.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = quiz.quizDay.dateTime.convertToLocalTimeFromUTC(),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Status: ${quiz.quizDay.status.convertFromEnum()}",
            style = MaterialTheme.typography.bodyMedium
        )

        when (quiz.quizDay.status) {
            Status.NOT_STARTED -> {
                Button(onClick = {
                    onEvent(QuizDetailsScreenUiEvent.StartQuiz(quiz.quizDay.id))
                }) {
                    Text("Start")
                }
            }
            Status.IN_PROGRESS -> {
                Button(onClick = {
                    onEvent(QuizDetailsScreenUiEvent.FinishQuiz(quiz.quizDay.id))
                }) {
                    Text("Finish")
                }
            }
            Status.COMPLETED -> {
                Button(onClick = {
                    onEvent(QuizDetailsScreenUiEvent.RevertQuiz(quiz.quizDay.id))
                }) {
                    Text("Revert")
                }
            }
            else -> {}
        }

        Button(onClick = onNavigateToSettings) {
            Text("Settings")
        }
    }
}
