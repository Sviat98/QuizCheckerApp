package com.bashkevich.quizchecker.core

import androidx.compose.runtime.Composable
import com.bashkevich.quizchecker.model.Status
import org.jetbrains.compose.resources.stringResource
import com.bashkevich.quizchecker.resources.*

@Composable
fun Status.toLocalizedString(): String {
    return when (this) {
        Status.NOT_STARTED -> stringResource(Res.string.status_not_started)
        Status.REGISTERED -> stringResource(Res.string.status_registered)
        Status.IN_PROGRESS -> stringResource(Res.string.status_in_progress)
        Status.COMPLETED -> stringResource(Res.string.status_completed)
    }
}
