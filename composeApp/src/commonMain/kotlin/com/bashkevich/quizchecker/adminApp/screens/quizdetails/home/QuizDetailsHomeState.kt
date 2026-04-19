package com.bashkevich.quizchecker.adminApp.screens.quizdetails.home

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState

@Immutable
data class QuizDetailsHomeState(
    val isLoading: Boolean = false,
) : UiState {
    companion object {
        fun initial() = QuizDetailsHomeState()
    }
}

sealed class QuizDetailsHomeEvent : UiEvent {
    data class StartQuiz(val quizId: String) : QuizDetailsHomeEvent()
    data class FinishQuiz(val quizId: String) : QuizDetailsHomeEvent()
    data class RevertQuiz(val quizId: String) : QuizDetailsHomeEvent()
}

sealed class QuizDetailsHomeAction : UiAction {
    object ShowErrorToast : QuizDetailsHomeAction()
}
