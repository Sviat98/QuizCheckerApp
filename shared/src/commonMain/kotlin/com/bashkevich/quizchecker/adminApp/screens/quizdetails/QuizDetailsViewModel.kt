package com.bashkevich.quizchecker.adminApp.screens.quizdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bashkevich.quizchecker.adminApp.QuizDetailsRoute
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.bashkevich.quizchecker.model.quiz.QuizRepository
import com.bashkevich.quizchecker.mvi.BaseViewModel

class QuizDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val quizRepository: QuizRepository
) : BaseViewModel<QuizDetailsScreenState, QuizDetailsScreenEvent, QuizDetailsScreenAction>() {

    private val _state = MutableStateFlow(QuizDetailsScreenState.Companion.initial())
    override val state: StateFlow<QuizDetailsScreenState>
        get() = _state.asStateFlow()

    val actions: Flow<QuizDetailsScreenAction>
        get() = super.action

    val quizId: String = savedStateHandle.toRoute<QuizDetailsRoute>().quizId

    init {
        loadQuizDetails()

        viewModelScope.launch {
            quizRepository.observeCurrentQuiz(quizId).collect { quiz ->
                reduceState { oldState ->
                    oldState.copy(quiz = quiz)
                }
            }
        }
    }

    private fun loadQuizDetails() {
        viewModelScope.launch {
            reduceState { oldState ->
                oldState.copy(isLoading = true)
            }
            quizRepository.getQuizEventById(quizId)
            reduceState { oldState ->
                oldState.copy(isLoading = false)
            }
        }
    }

    fun onEvent(uiEvent: QuizDetailsScreenEvent) {
        when (uiEvent) {
            is QuizDetailsScreenEvent.LoadQuizDetails -> loadQuizDetails()
        }
    }

    private fun reduceState(reducer: (QuizDetailsScreenState) -> QuizDetailsScreenState) {
        _state.update(reducer)
    }
}
