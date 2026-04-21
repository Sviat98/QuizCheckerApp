package com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState
import com.bashkevich.quizchecker.model.blank_template.domain.AnswerTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate

sealed interface SlotAnswersState {
    data object Idle : SlotAnswersState
    data object Loading : SlotAnswersState
    data class Success(val answers: List<AnswerTemplate>) : SlotAnswersState
    data class Error(val message: String?) : SlotAnswersState
}

@Immutable
data class QuizDetailsBlanksState(
    val isLoading: Boolean = false,
    val blankTemplates: List<BlankTemplate> = emptyList(),
    val error: String? = null,
    val slotAnswersStates: Map<Int, SlotAnswersState> = emptyMap(),
    val newBlankText: String = ""
) : UiState {
    companion object {
        fun initial() = QuizDetailsBlanksState()
    }
}

sealed class QuizDetailsBlanksEvent : UiEvent {
    data class LoadSlotAnswers(val slotId: Int) : QuizDetailsBlanksEvent()
    data class OnNewBlankTextChanged(val text: String) : QuizDetailsBlanksEvent()
}

sealed class QuizDetailsBlanksAction : UiAction
