package com.bashkevich.quizchecker.adminApp.screens.quizdetails

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState
import com.bashkevich.quizchecker.model.quiz.domain.Quiz

@Immutable
data class QuizDetailsScreenState(
    val isLoading: Boolean,
    val quiz: Quiz = Quiz.DEFAULT,
) : UiState {
    companion object {
        fun initial() = QuizDetailsScreenState(
            isLoading = true,
            quiz = Quiz.DEFAULT,
        )
    }
}

sealed class QuizDetailsScreenEvent : UiEvent {
    data object LoadQuizDetails : QuizDetailsScreenEvent()
}

sealed class QuizDetailsScreenAction : UiAction {
    object ShowErrorToast : QuizDetailsScreenAction()
}
