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
import com.bashkevich.quizchecker.model.quiz.domain.Quiz

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
                text = "Status: ${quiz.quizDay.status}"
                //.convertToLocalTimeFromUTC()
                ,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = if (quiz.quizDay.registrationOpen ) "Registration started at ${
                    quiz.quizDay.registrationTimeBegin.convertToLocalTimeFromUTC()
                }" else "Registration starts at ${
                    quiz.quizDay.registrationTimeBegin.convertToLocalTimeFromUTC()
                }",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

