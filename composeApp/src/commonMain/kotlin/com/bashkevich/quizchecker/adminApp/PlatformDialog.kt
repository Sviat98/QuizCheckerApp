package com.bashkevich.quizchecker.adminApp

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
)
