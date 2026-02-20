package com.bashkevich.quizchecker.adminApp.screens.addquiz

import androidx.lifecycle.viewModelScope
import com.bashkevich.quizchecker.core.convertToUTCFromLocalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import com.bashkevich.quizchecker.model.quiz.QuizRepository
import com.bashkevich.quizchecker.mvi.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddQuizViewModel(
    private val quizRepository: QuizRepository
) : BaseViewModel<AddQuizScreenState, AddQuizScreenUiEvent, AddQuizScreenAction>() {

    private val _state = MutableStateFlow(AddQuizScreenState.initial())
    override val state: StateFlow<AddQuizScreenState>
        get() = _state.asStateFlow()

    val actions: Flow<AddQuizScreenAction>
        get() = super.action

    fun addQuiz() {
        viewModelScope.launch {
            val state = state.value

            val eventDateTime =
                LocalDateTime(state.eventDate!!, state.eventTime!!).convertToUTCFromLocalTime()

            val registrationDateTime = LocalDateTime(
                state.registrationDate!!,
                state.registrationTime!!
            ).convertToUTCFromLocalTime()

            quizRepository.addQuiz(state.title, eventDateTime, registrationDateTime, state.city)
        }
    }

    fun onEvent(uiEvent: AddQuizScreenUiEvent) {
        // some feature-specific logic
        when (uiEvent) {
            is AddQuizScreenUiEvent.OnTitleChange -> {
                reduceState { oldState ->
                    oldState.copy(title = uiEvent.text)
                }
            }

            is AddQuizScreenUiEvent.OnDateEventChange -> {
                reduceState { oldState ->
                    oldState.copy(eventDate = uiEvent.date)
                }
            }

            is AddQuizScreenUiEvent.OnTimeEventChange -> {
                reduceState { oldState ->
                    oldState.copy(eventTime = uiEvent.time)
                }
            }

            is AddQuizScreenUiEvent.OnDateRegChange -> {
                reduceState { oldState ->
                    oldState.copy(registrationDate = uiEvent.date)
                }
            }

            is AddQuizScreenUiEvent.OnTimeRegChange -> {
                reduceState { oldState ->
                    oldState.copy(registrationTime = uiEvent.time)
                }
            }

            is AddQuizScreenUiEvent.OnCityChange -> {
                reduceState { oldState ->
                    oldState.copy(city = uiEvent.city)
                }
            }

            is AddQuizScreenUiEvent.SetDialogState -> {
                reduceState { oldState ->
                    oldState.copy(addQuizDialogState = uiEvent.addQuizDialogState)
                }
            }

            AddQuizScreenUiEvent.AddQuiz -> {
                addQuiz()
            }
        }
    }

    private fun reduceState(reducer: (AddQuizScreenState) -> AddQuizScreenState) {
        _state.update(reducer)
    }
}