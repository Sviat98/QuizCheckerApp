package com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bashkevich.quizchecker.adminApp.QuizDetailsRoute
import com.bashkevich.quizchecker.model.blank_template.BlankTemplateRepository
import com.bashkevich.quizchecker.mvi.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizDetailsBlanksViewModel(
    savedStateHandle: SavedStateHandle,
    private val blankTemplateRepository: BlankTemplateRepository
) : BaseViewModel<QuizDetailsBlanksState, QuizDetailsBlanksEvent, QuizDetailsBlanksAction>() {

    private val _state = MutableStateFlow(QuizDetailsBlanksState.initial())
    override val state: StateFlow<QuizDetailsBlanksState>
        get() = _state.asStateFlow()

    val actions: Flow<QuizDetailsBlanksAction>
        get() = super.action

    private val quizId: String = savedStateHandle.toRoute<QuizDetailsRoute>().quizId

    init {
        loadBlankTemplates()
    }

    private fun loadBlankTemplates() {
        viewModelScope.launch {
            reduceState { it.copy(isLoading = true, error = null) }
            when (val result = blankTemplateRepository.getBlankTemplates(quizId)) {
                is com.bashkevich.quizchecker.core.ktor.LoadResult.Success -> {
                    reduceState { it.copy(isLoading = false, blankTemplates = result.result) }
                }
                is com.bashkevich.quizchecker.core.ktor.LoadResult.Error -> {
                    reduceState { it.copy(isLoading = false, error = result.result.message) }
                }
            }
        }
    }

    fun onEvent(uiEvent: QuizDetailsBlanksEvent) {
        when (uiEvent) {
            is QuizDetailsBlanksEvent.LoadSlotAnswers -> loadSlotAnswers(uiEvent.slotId)
            is QuizDetailsBlanksEvent.OnNewBlankTextChanged -> updateNewBlankText(uiEvent.text)
        }
    }

    private fun loadSlotAnswers(slotId: Int) {
        val currentState = _state.value
        val currentSlotState = currentState.slotAnswersStates[slotId]

        if (currentSlotState is SlotAnswersState.Success) return

        viewModelScope.launch {
            reduceState {
                it.copy(slotAnswersStates = it.slotAnswersStates + (slotId to SlotAnswersState.Loading))
            }
            when (val result = blankTemplateRepository.getSlotAnswers(slotId)) {
                is com.bashkevich.quizchecker.core.ktor.LoadResult.Success -> {
                    reduceState {
                        it.copy(slotAnswersStates = it.slotAnswersStates + (slotId to SlotAnswersState.Success(result.result)))
                    }
                }
                is com.bashkevich.quizchecker.core.ktor.LoadResult.Error -> {
                    reduceState {
                        it.copy(slotAnswersStates = it.slotAnswersStates + (slotId to SlotAnswersState.Error(result.result.message)))
                    }
                }
            }
        }
    }

    private fun updateNewBlankText(text: String) {
        reduceState { it.copy(newBlankText = text) }
    }

    private fun reduceState(reducer: (QuizDetailsBlanksState) -> QuizDetailsBlanksState) {
        _state.update(reducer)
    }
}
