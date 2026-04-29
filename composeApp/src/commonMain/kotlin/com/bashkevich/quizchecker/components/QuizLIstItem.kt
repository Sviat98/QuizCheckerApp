package com.bashkevich.quizchecker.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bashkevich.quizchecker.core.convertToLocalTimeFromUTC
import com.bashkevich.quizchecker.core.formatDate
import com.bashkevich.quizchecker.core.formatTime
import com.bashkevich.quizchecker.core.toLocalizedString
import com.bashkevich.quizchecker.core.toLocalDateTimeFromUTC
import com.bashkevich.quizchecker.model.quiz.domain.Quiz
import org.jetbrains.compose.resources.stringResource
import com.bashkevich.quizchecker.resources.*

@Composable
fun QuizWeekItem(
    quiz: Quiz,
    onItemSelected: (Quiz) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onItemSelected.invoke(quiz) }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = quiz.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = quiz.quizDay.dateTime.convertToLocalTimeFromUTC()
                ,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${stringResource(Res.string.status_label)}: ${quiz.quizDay.status.toLocalizedString()}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = if (quiz.quizDay.registrationOpen) {
                    stringResource(Res.string.registration_started)
                } else {
                    stringResource(
                        Res.string.registration_starts_at,
                        quiz.quizDay.registrationTimeBegin.toLocalDateTimeFromUTC().date.formatDate(),
                        quiz.quizDay.registrationTimeBegin.toLocalDateTimeFromUTC().time.formatTime()
                    )
                },
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

