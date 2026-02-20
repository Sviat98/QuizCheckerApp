package com.bashkevich.quizchecker.adminApp.screens.quizlist

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.bashkevich.quizchecker.model.quiz.QuizRepository
import com.bashkevich.quizchecker.mvi.BaseViewModel


class QuizListViewModel(
    private val quizRepository: QuizRepository
) : BaseViewModel<QuizListScreenState, QuizListScreenUiEvent, QuizListScreenAction>() {

    private val _state = MutableStateFlow(QuizListScreenState.Companion.initial())
    override val state: StateFlow<QuizListScreenState>
        get() = _state.asStateFlow()

    val actions: Flow<QuizListScreenAction>
        get() = super.action

    init {
        loadQuizListResult()

        viewModelScope.launch {
            quizRepository.observeQuizList().distinctUntilChanged()
                .collect{ quizList ->
                    reduceState { oldState->
                        oldState.copy(quizList = quizList)
                    }
                }
        }
    }

//    fun setDialogState(quizListDialogState: QuizListDialogState) {
//        sendEvent(QuizListScreenUiEvent.SetDialogState(quizListDialogState))
//    }

//    fun selectQuiz(quiz: Quiz) {
//        viewModelScope.launch {
//
//            quizRepository.setCurrentQuizId(quiz.quizDay.id)
//        }
//
//    }


    private fun loadQuizListResult() {
        viewModelScope.launch {
            reduceState { oldState ->
                oldState.copy(isLoading = true)
            }
            quizRepository.getQuizListResult()

            reduceState { oldState ->
                oldState.copy(isLoading = false)
            }
        }
    }

    fun onEvent(uiEvent: QuizListScreenUiEvent) {
        // some feature-specific logic
        when (uiEvent) {
            is QuizListScreenUiEvent.LoadQuizList -> {
                loadQuizListResult()
            }
        }
    }

    private fun reduceState(reducer: (QuizListScreenState) -> QuizListScreenState) {
        _state.update(reducer)
    }
}