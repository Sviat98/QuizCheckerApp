package com.bashkevich.quizchecker.adminApp.screens.quizdetails

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState
import com.bashkevich.quizchecker.model.quiz.domain.Quiz

@Immutable
sealed class QuizDetailsScreenUiEvent : UiEvent {
    data object LoadQuizDetails : QuizDetailsScreenUiEvent()
    data class StartQuiz(val quizId: String) : QuizDetailsScreenUiEvent()
    data class FinishQuiz(val quizId: String) : QuizDetailsScreenUiEvent()
    data class RevertQuiz(val quizId: String) : QuizDetailsScreenUiEvent()
}

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

@Immutable
sealed class QuizDetailsDialogState {
    object None : QuizDetailsDialogState()
}

@Immutable
sealed class QuizDetailsScreenAction : UiAction {
    object ShowErrorToast : QuizDetailsScreenAction()
}
