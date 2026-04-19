package com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.bashkevich.quizchecker.mvi.BaseViewModel

class QuizDetailsBlanksViewModel :
    BaseViewModel<QuizDetailsBlanksState, QuizDetailsBlanksEvent, QuizDetailsBlanksAction>() {

    private val _state = MutableStateFlow(QuizDetailsBlanksState.initial())
    override val state: StateFlow<QuizDetailsBlanksState>
        get() = _state.asStateFlow()

    val actions: Flow<QuizDetailsBlanksAction>
        get() = super.action

    fun onEvent(uiEvent: QuizDetailsBlanksEvent) {}
}
