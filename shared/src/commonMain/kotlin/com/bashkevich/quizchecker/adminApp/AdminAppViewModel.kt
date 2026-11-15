package com.bashkevich.quizchecker.adminApp

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashkevich.quizchecker.mvi.UiState
import com.bashkevich.quizchecker.settings.domain.SettingsLocale
import com.bashkevich.quizchecker.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class AdminAppViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AdminAppState.initial())

    val state: StateFlow<AdminAppState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.observeLocale().distinctUntilChanged().collect { locale ->
                _state.value = _state.value.copy(locale = locale)
                println("AdminAppViewModel locale = $locale")
            }
        }
    }
}

@Immutable
data class AdminAppState(
    val locale: SettingsLocale
) : UiState {
    companion object {
        fun initial() = AdminAppState(
            locale = SettingsLocale.English_UK
        )
    }
}
