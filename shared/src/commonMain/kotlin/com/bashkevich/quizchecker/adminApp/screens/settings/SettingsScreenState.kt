package com.bashkevich.quizchecker.adminApp.screens.settings

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState
import com.bashkevich.quizchecker.settings.domain.SettingsLocale

@Immutable
sealed class SettingsScreenUiEvent : UiEvent {
    data class OnLocaleChange(val locale: SettingsLocale) : SettingsScreenUiEvent()
}

@Immutable
data class SettingsScreenState(
    val sample: String = ""
) : UiState {
    companion object {
        fun initial() = SettingsScreenState()
    }
}

@Immutable
sealed class SettingsScreenAction : UiAction {
    object ShowErrorToast : SettingsScreenAction()
}
