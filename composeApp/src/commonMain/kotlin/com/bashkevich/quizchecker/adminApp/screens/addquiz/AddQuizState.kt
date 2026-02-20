package com.bashkevich.quizchecker.adminApp.screens.addquiz

import androidx.compose.runtime.Immutable
import com.bashkevich.quizchecker.mvi.UiAction
import com.bashkevich.quizchecker.mvi.UiEvent
import com.bashkevich.quizchecker.mvi.UiState
import kotlinx.datetime.*

@Immutable
sealed class AddQuizScreenUiEvent : UiEvent {


//    data class ShowQuizList(val quizList: List<Quiz>) : QuizListScreenUiEvent()
//
    data class SetDialogState(val addQuizDialogState: AddQuizDialogState): AddQuizScreenUiEvent()
    data class OnTitleChange(val text: String): AddQuizScreenUiEvent()

    data class OnDateEventChange(val date: LocalDate): AddQuizScreenUiEvent()
    data class OnTimeEventChange(val time: LocalTime): AddQuizScreenUiEvent()
    data class OnDateRegChange(val date: LocalDate): AddQuizScreenUiEvent()
    data class OnTimeRegChange(val time: LocalTime): AddQuizScreenUiEvent()

    data class OnCityChange(val city: String): AddQuizScreenUiEvent()

    data object AddQuiz : AddQuizScreenUiEvent()
}

@Immutable
data class AddQuizScreenState(
    val title: String,
    val eventDate: LocalDate?,
    val eventTime: LocalTime?,
    val city: String,
    val registrationDate: LocalDate?,
    val registrationTime: LocalTime?,
    val addQuizDialogState: AddQuizDialogState
) : UiState {
    companion object {
        fun initial() = AddQuizScreenState(
            title = "",
            eventDate =null,
            eventTime = null,
            city = "",
            registrationDate = null,
            registrationTime = null,
            addQuizDialogState = AddQuizDialogState.None
        )
    }

//    override fun toString(): String {
//        //return "isLoading: $isLoading, data.size: ${data.size}, isShowAddDialog: $isShowAddDialog"
//        return "loading State $loadingState"
//    }
}

//@Immutable
//sealed class HomeScreenLoadingState {
//    object Loading : HomeScreenLoadingState()
//    object Refreshing : HomeScreenLoadingState()
//    object InitialError : HomeScreenLoadingState()
//    object Success : HomeScreenLoadingState()
//}

@Immutable
sealed class AddQuizDialogState {
    object None : AddQuizDialogState()
    object DateEventDialogState : AddQuizDialogState()
    object TimeEventDialogState : AddQuizDialogState()
    object DateRegDialogState : AddQuizDialogState()
    object TimeRegDialogState : AddQuizDialogState()

}
//
//
@Immutable
sealed class AddQuizScreenAction : UiAction {

    object ShowErrorToast: AddQuizScreenAction()

}