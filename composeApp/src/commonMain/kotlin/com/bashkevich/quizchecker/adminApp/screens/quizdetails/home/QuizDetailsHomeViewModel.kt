package com.bashkevich.quizchecker.adminApp.screens.quizdetails.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.bashkevich.quizchecker.model.quiz.QuizRepository
import com.bashkevich.quizchecker.mvi.BaseViewModel

class QuizDetailsHomeViewModel(
    private val quizRepository: QuizRepository
) : BaseViewModel<QuizDetailsHomeState, QuizDetailsHomeEvent, QuizDetailsHomeAction>() {

    private val _state = MutableStateFlow(QuizDetailsHomeState.initial())
    override val state: StateFlow<QuizDetailsHomeState>
        get() = _state.asStateFlow()

    val actions: Flow<QuizDetailsHomeAction>
        get() = super.action

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

    fun onEvent(uiEvent: QuizDetailsHomeEvent) {
        when (uiEvent) {
            is QuizDetailsHomeEvent.StartQuiz -> startQuiz(uiEvent.quizId)
            is QuizDetailsHomeEvent.FinishQuiz -> finishQuiz(uiEvent.quizId)
            is QuizDetailsHomeEvent.RevertQuiz -> revertQuiz(uiEvent.quizId)
        }
    }

    private fun reduceState(reducer: (QuizDetailsHomeState) -> QuizDetailsHomeState) {
        _state.update(reducer)
    }
}
