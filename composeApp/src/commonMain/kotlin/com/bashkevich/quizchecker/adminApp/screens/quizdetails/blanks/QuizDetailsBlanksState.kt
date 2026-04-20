package com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate

@Immutable
data class QuizDetailsBlanksState(
    val isLoading: Boolean = false,
    val blankTemplates: List<BlankTemplate> = emptyList(),
    val error: String? = null
) : UiState {
    companion object {
        fun initial() = QuizDetailsBlanksState()
    }
}

sealed class QuizDetailsBlanksEvent : UiEvent

sealed class QuizDetailsBlanksAction : UiAction
