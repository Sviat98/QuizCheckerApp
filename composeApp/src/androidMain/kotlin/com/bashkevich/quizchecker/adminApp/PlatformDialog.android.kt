package com.bashkevich.quizchecker.adminApp

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
actual fun PlatformDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        content = content
    )
}
