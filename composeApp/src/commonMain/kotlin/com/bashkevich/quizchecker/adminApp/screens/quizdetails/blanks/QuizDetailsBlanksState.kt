package com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState

@Immutable
data class QuizDetailsBlanksState(
    val isLoading: Boolean = false,
) : UiState {
    companion object {
        fun initial() = QuizDetailsBlanksState()
    }
}

sealed class QuizDetailsBlanksEvent : UiEvent

sealed class QuizDetailsBlanksAction : UiAction
