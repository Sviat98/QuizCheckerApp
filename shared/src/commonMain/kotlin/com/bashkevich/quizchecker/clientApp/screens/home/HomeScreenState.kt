package com.bashkevich.quizchecker.clientApp.screens.home

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState
import com.bashkevich.quizchecker.model.quiz.domain.Quiz

@Immutable
sealed class HomeScreenUiEvent : UiEvent

@Immutable
data class HomeScreenState(
    val quizList: List<Quiz> = emptyList()
) : UiState

@Immutable
sealed class HomeScreenAction : UiAction
