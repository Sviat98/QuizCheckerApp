package com.bashkevich.quizchecker.adminApp.screens.quizdetails.standings

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState

@Immutable
data class QuizDetailsStandingsState(
    val isLoading: Boolean = false,
) : UiState {
    companion object {
        fun initial() = QuizDetailsStandingsState()
    }
}

sealed class QuizDetailsStandingsEvent : UiEvent

sealed class QuizDetailsStandingsAction : UiAction
