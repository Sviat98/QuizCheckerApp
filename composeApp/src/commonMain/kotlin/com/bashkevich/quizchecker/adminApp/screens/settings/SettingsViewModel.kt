package com.bashkevich.quizchecker.adminApp.screens.settings

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.bashkevich.quizchecker.mvi.BaseViewModel

class SettingsViewModel : BaseViewModel<SettingsScreenState, SettingsScreenUiEvent, SettingsScreenAction>() {

    private val _state = MutableStateFlow(SettingsScreenState.Companion.initial())
    override val state: StateFlow<SettingsScreenState>
        get() = _state.asStateFlow()

    val actions: StateFlow<SettingsScreenAction>
        get() = super.action as StateFlow<SettingsScreenAction>

    fun onEvent(uiEvent: SettingsScreenUiEvent) {
        when (uiEvent) {
            is SettingsScreenUiEvent.OnSettingsClick -> {
                // Handle settings click
            }
        }
    }

    private fun reduceState(reducer: (SettingsScreenState) -> SettingsScreenState) {
        _state.value = reducer(_state.value)
    }
}
