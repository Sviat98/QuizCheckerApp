package com.bashkevich.quizchecker.clientApp.screens.quizlist

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.model.quiz.domain.Quiz
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState

@Immutable
sealed class QuizListScreenUiEvent : UiEvent {
    data object RefreshQuizList : QuizListScreenUiEvent()
    data class SelectQuiz(val quiz: Quiz) : QuizListScreenUiEvent()
}

@Immutable
data class QuizListScreenState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val quizList: List<Quiz> = emptyList(),
    val currentQuizId: String = ""
) : UiState

@Immutable
sealed class QuizListScreenAction : UiAction {
    object NavigateBack : QuizListScreenAction()
}
