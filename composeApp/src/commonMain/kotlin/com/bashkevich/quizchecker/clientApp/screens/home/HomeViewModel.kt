package com.bashkevich.quizchecker.clientApp.screens.home

import androidx.lifecycle.viewModelScope
import com.bashkevich.quizchecker.mvi.BaseViewModel
import com.bashkevich.quizchecker.model.quiz.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val quizRepository: QuizRepository
) : BaseViewModel<HomeScreenState, HomeScreenUiEvent, HomeScreenAction>() {

    private val _state = MutableStateFlow(HomeScreenState())
    override val state: StateFlow<HomeScreenState>
        get() = _state.asStateFlow()

    private var hasAutoSelected = false

    init {
        viewModelScope.launch {
            quizRepository.getQuizListResult()
        }

        viewModelScope.launch {
            quizRepository.observeQuizList().distinctUntilChanged()
                .collect { quizList ->
                    _state.update { it.copy(quizList = quizList) }

                    if (!hasAutoSelected && quizList.isNotEmpty()) {
                        hasAutoSelected = true
                        quizRepository.setCurrentQuizId(quizList.first().quizDay.id)
                    }
                }
        }
    }

    fun onEvent(uiEvent: HomeScreenUiEvent) {}
}
