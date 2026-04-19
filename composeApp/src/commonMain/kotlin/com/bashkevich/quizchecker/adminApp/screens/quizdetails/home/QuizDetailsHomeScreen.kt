package com.bashkevich.quizchecker.adminApp.screens.quizdetails.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bashkevich.quizchecker.core.toLocalizedString
import com.bashkevich.quizchecker.core.convertToLocalTimeFromUTC
import com.bashkevich.quizchecker.model.Status
import com.bashkevich.quizchecker.model.quiz.domain.Quiz
import org.jetbrains.compose.resources.stringResource
import com.bashkevich.quizchecker.resources.*

@Composable
fun QuizDetailsHomeScreen(
    modifier: Modifier = Modifier,
    quiz: Quiz,
    onEvent: (QuizDetailsHomeEvent) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
            text = "${stringResource(Res.string.status_label)}: ${quiz.quizDay.status.toLocalizedString()}",
            style = MaterialTheme.typography.bodyMedium
        )

        when (quiz.quizDay.status) {
            Status.NOT_STARTED -> {
                Button(onClick = {
                    onEvent(QuizDetailsHomeEvent.StartQuiz(quiz.quizDay.id))
                }) {
                    Text(stringResource(Res.string.start_button))
                }
            }
            Status.IN_PROGRESS -> {
                Button(onClick = {
                    onEvent(QuizDetailsHomeEvent.FinishQuiz(quiz.quizDay.id))
                }) {
                    Text(stringResource(Res.string.finish_button))
                }
            }
            Status.COMPLETED -> {
                Button(onClick = {
                    onEvent(QuizDetailsHomeEvent.RevertQuiz(quiz.quizDay.id))
                }) {
                    Text(stringResource(Res.string.revert_button))
                }
            }
            else -> {}
        }

        Button(onClick = onNavigateToSettings) {
            Text(stringResource(Res.string.settings_title))
        }
    }
}
