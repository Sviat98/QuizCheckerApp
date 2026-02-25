package com.bashkevich.quizchecker.adminApp.screens.settings

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState

@Immutable
sealed class SettingsScreenUiEvent : UiEvent {
    data object OnSettingsClick : SettingsScreenUiEvent()
}

@Immutable
data class SettingsScreenState(
) : UiState {
    companion object {
        fun initial() = SettingsScreenState()
    }
}

@Immutable
sealed class SettingsScreenAction : UiAction {
    object ShowErrorToast : SettingsScreenAction()
}
