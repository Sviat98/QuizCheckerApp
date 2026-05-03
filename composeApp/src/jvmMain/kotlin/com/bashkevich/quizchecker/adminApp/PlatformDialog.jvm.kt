package com.bashkevich.quizchecker.adminApp

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.DialogWindowScope
import androidx.compose.ui.window.rememberDialogState

@Composable
actual fun PlatformDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    DialogWindow(
        onCloseRequest = onDismissRequest,
        state = rememberDialogState(width = 500.dp, height = 600.dp),
        content = { content() }
    )
}
