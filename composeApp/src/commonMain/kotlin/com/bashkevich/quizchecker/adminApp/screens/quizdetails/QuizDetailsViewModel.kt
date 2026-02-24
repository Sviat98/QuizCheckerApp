package com.bashkevich.quizchecker.adminApp.screens.quizdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bashkevich.quizchecker.adminApp.QuizDetailsRoute
import com.bashkevich.quizchecker.core.ktor.LoadResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.bashkevich.quizchecker.model.quiz.QuizRepository
import com.bashkevich.quizchecker.mvi.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class QuizDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val quizRepository: QuizRepository
) : BaseViewModel<QuizDetailsScreenState, QuizDetailsScreenUiEvent, QuizDetailsScreenAction>() {

    private val _state = MutableStateFlow(QuizDetailsScreenState.Companion.initial())
    override val state: StateFlow<QuizDetailsScreenState>
        get() = _state.asStateFlow()

    val actions: Flow<QuizDetailsScreenAction>
        get() = super.action

    private val quizId = savedStateHandle.toRoute<QuizDetailsRoute>().quizId

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

    private fun startQuiz(quizId: String) {
        viewModelScope.launch {
            quizRepository.startQuiz(quizId)
        }
    }

    private fun finishQuiz(quizId: String) {
        viewModelScope.launch {
            quizRepository.finishQuiz(quizId)
        }
    }

    private fun revertQuiz(quizId: String) {
        viewModelScope.launch {
            quizRepository.revertQuiz(quizId)
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

    fun onEvent(uiEvent: QuizDetailsScreenUiEvent) {
        when (uiEvent) {
            is QuizDetailsScreenUiEvent.LoadQuizDetails -> {
                loadQuizDetails()
            }
            is QuizDetailsScreenUiEvent.StartQuiz -> {
                startQuiz(uiEvent.quizId)
            }
            is QuizDetailsScreenUiEvent.FinishQuiz -> {
                finishQuiz(uiEvent.quizId)
            }
            is QuizDetailsScreenUiEvent.RevertQuiz -> {
                revertQuiz(uiEvent.quizId)
            }
        }
    }

    private fun reduceState(reducer: (QuizDetailsScreenState) -> QuizDetailsScreenState) {
        _state.update(reducer)
    }
}
