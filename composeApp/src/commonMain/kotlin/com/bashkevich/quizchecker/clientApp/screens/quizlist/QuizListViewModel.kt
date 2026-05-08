package com.bashkevich.quizchecker.clientApp.screens.quizlist

import androidx.lifecycle.viewModelScope
import com.bashkevich.quizchecker.mvi.BaseViewModel
import com.bashkevich.quizchecker.model.quiz.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizListViewModel(
    private val quizRepository: QuizRepository
) : BaseViewModel<QuizListScreenState, QuizListScreenUiEvent, QuizListScreenAction>() {

    private val _state = MutableStateFlow(QuizListScreenState())
    override val state: StateFlow<QuizListScreenState>
        get() = _state.asStateFlow()

    val actions: Flow<QuizListScreenAction>
        get() = super.action

    init {
        viewModelScope.launch {
            quizRepository.observeQuizList().distinctUntilChanged()
                .collect { quizList ->
                    _state.update { it.copy(quizList = quizList) }
                }
        }

        viewModelScope.launch {
            quizRepository.observeCurrentQuizId()
                .collect { currentQuizId ->
                    _state.update { it.copy(currentQuizId = currentQuizId) }
                }
        }
    }

    fun onEvent(uiEvent: QuizListScreenUiEvent) {
        when (uiEvent) {
            is QuizListScreenUiEvent.RefreshQuizList -> refreshQuizList()
            is QuizListScreenUiEvent.SelectQuiz -> selectQuiz(uiEvent.quiz)
        }
    }

    private fun refreshQuizList() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            quizRepository.getQuizListResult()
            _state.update { it.copy(isRefreshing = false) }
        }
    }

    private fun selectQuiz(quiz: com.bashkevich.quizchecker.model.quiz.domain.Quiz) {
        viewModelScope.launch {
            quizRepository.setCurrentQuizId(quiz.quizDay.id)
            sendAction(QuizListScreenAction.NavigateBack)
        }
    }
}
