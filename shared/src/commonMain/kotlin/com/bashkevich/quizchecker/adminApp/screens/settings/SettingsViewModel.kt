package com.bashkevich.quizchecker.adminApp.screens.settings

import androidx.lifecycle.viewModelScope
import com.bashkevich.quizchecker.mvi.BaseViewModel
import com.bashkevich.quizchecker.settings.LocalLocalization
import com.bashkevich.quizchecker.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : BaseViewModel<SettingsScreenState, SettingsScreenUiEvent, SettingsScreenAction>() {

    private val _state = MutableStateFlow(SettingsScreenState.Companion.initial())
    override val state: StateFlow<SettingsScreenState>
        get() = _state.asStateFlow()

    val actions: StateFlow<SettingsScreenAction>
        get() = super.action as StateFlow<SettingsScreenAction>

    fun onEvent(uiEvent: SettingsScreenUiEvent) {
        when (uiEvent) {
            is SettingsScreenUiEvent.OnLocaleChange -> {
                viewModelScope.launch {
                    settingsRepository.setLocale(uiEvent.locale)
                }
            }
        }
    }

    private fun reduceState(reducer: (SettingsScreenState) -> SettingsScreenState) {
        _state.value = reducer(_state.value)
    }
}
