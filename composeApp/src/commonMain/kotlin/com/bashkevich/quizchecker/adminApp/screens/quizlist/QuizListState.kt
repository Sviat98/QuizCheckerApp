package com.bashkevich.quizchecker.adminApp.screens.quizlist

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.model.quiz.domain.Quiz
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState

@Immutable
sealed class QuizListScreenUiEvent : UiEvent {
    data object LoadQuizList : QuizListScreenUiEvent()

    //data class SetDialogState(val quizListDialogState: QuizListDialogState): QuizListScreenUiEvent()
}

@Immutable
data class QuizListScreenState(
    val isLoading: Boolean,
    val quizList: List<Quiz> = emptyList(),
    val quizListDialogState: QuizListDialogState
) : UiState {
    companion object {
        fun initial() = QuizListScreenState(
            isLoading = true,
            quizList = emptyList(),
            quizListDialogState = QuizListDialogState.None
        )
    }
}

@Immutable
sealed class QuizListDialogState {
    object None : QuizListDialogState()
    object AddQuiz : QuizListDialogState()
}

@Immutable
sealed class QuizListScreenAction : UiAction {

    object ShowErrorToast: QuizListScreenAction()

}