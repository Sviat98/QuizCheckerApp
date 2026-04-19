package com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import com.bashkevich.quizchecker.resources.*

@Composable
fun QuizDetailsBlanksScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.blanks_tab_title),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
