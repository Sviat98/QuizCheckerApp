package com.bashkevich.quizchecker.adminApp.screens.quizdetails.standings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.bashkevich.quizchecker.mvi.BaseViewModel

class QuizDetailsStandingsViewModel :
    BaseViewModel<QuizDetailsStandingsState, QuizDetailsStandingsEvent, QuizDetailsStandingsAction>() {

    private val _state = MutableStateFlow(QuizDetailsStandingsState.initial())
    override val state: StateFlow<QuizDetailsStandingsState>
        get() = _state.asStateFlow()

    val actions: Flow<QuizDetailsStandingsAction>
        get() = super.action

    fun onEvent(uiEvent: QuizDetailsStandingsEvent) {}
}
