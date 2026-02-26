package com.bashkevich.quizchecker.adminApp.screens.quizlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import quizcheckerapp.composeapp.generated.resources.*

@Composable
fun QuizListEmpty(
    onAddQuizClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            //.background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(Res.string.event_schedule_title),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(Res.string.no_items_text))
            Button(
                onClick = onAddQuizClick,
            ) {
                Text(stringResource(Res.string.add_quiz_button))
            }
        }


    }
}