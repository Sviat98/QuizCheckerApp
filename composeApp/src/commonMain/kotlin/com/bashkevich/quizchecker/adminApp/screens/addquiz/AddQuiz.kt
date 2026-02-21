package com.bashkevich.quizchecker.adminApp.screens.addquiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bashkevich.quizchecker.adminApp.LocalNavHostController
import com.bashkevich.quizchecker.components.icons.IconGroup
import com.bashkevich.quizchecker.components.icons.default_icons.ArrowDropDown
import com.bashkevich.quizchecker.components.icons.default_icons.CalendarMonth
import com.bashkevich.quizchecker.components.icons.default_icons.Schedule
import com.bashkevich.quizchecker.core.formatDate
import com.bashkevich.quizchecker.core.formatTime


@Composable
fun AddQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: AddQuizViewModel,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val dialogState =
        rememberDialogState(width = 500.dp, height = 600.dp)

    val navController = LocalNavHostController.current

    DialogWindow(onCloseRequest = {
        navController.navigateUp()
    }, state = dialogState) {
        AddQuizContent(
            addQuizScreenState = state,
            onEvent = { viewModel.onEvent(it) }
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuizContent(
    addQuizScreenState: AddQuizScreenState,
    onEvent: (AddQuizScreenUiEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val titleFieldState = rememberTextFieldState()


        TextField(
            value = addQuizScreenState.title,
            onValueChange = { onEvent(AddQuizScreenUiEvent.OnTitleChange(it)) },
            placeholder = { Text("Title") })


        TextField(
            value = addQuizScreenState.eventDate.formatDate(),
            onValueChange = { },
            placeholder = { Text("Date") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    onEvent(
                        AddQuizScreenUiEvent.SetDialogState(
                            AddQuizDialogState.DateEventDialogState
                        )
                    )
                }) {
                    Icon(IconGroup.Default.CalendarMonth, contentDescription = "Select quiz date")
                }
            })

        TextField(
            value = addQuizScreenState.eventTime.formatTime(),
            onValueChange = { },
            placeholder = { Text("Time") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    onEvent(
                        AddQuizScreenUiEvent.SetDialogState(
                            AddQuizDialogState.TimeEventDialogState
                        )
                    )
                }) {
                    Icon(IconGroup.Default.Schedule, contentDescription = "Select quiz time")
                }
            })

        TextField(
            value = addQuizScreenState.registrationDate.formatDate(),
            onValueChange = { },
            placeholder = { Text("Registration Date") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    onEvent(
                        AddQuizScreenUiEvent.SetDialogState(
                            AddQuizDialogState.DateRegDialogState
                        )
                    )
                }) {
                    Icon(IconGroup.Default.CalendarMonth, contentDescription = "Select registration date")
                }
            })


        TextField(
            value = addQuizScreenState.registrationTime.formatTime(),
            onValueChange = { },
            placeholder = { Text("Registration Time") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    onEvent(
                        AddQuizScreenUiEvent.SetDialogState(
                            AddQuizDialogState.TimeRegDialogState
                        )
                    )
                }) {
                    Icon(IconGroup.Default.Schedule, contentDescription = "Select registration time")
                }
            })

        var expanded by remember {
            mutableStateOf(false)
        }

        var textfieldSize by remember { mutableStateOf(Size.Zero) }
        Box {
            TextField(
                value = addQuizScreenState.city,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier
                    //.fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldSize = coordinates.size.toSize()
                    },
                placeholder = { Text("City") },
                trailingIcon = {
                    Icon(
                        IconGroup.Default.ArrowDropDown, "select city",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) {
                        textfieldSize.width.toDp()
                    })
            ) {
                listOf("Minsk", "Grodno").forEach { cityItem ->
                    DropdownMenuItem(
                        onClick = {
                            onEvent(AddQuizScreenUiEvent.OnCityChange(cityItem))
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(cityItem) }
                    )
                }
            }
        }


        Button(onClick = {
            onEvent(AddQuizScreenUiEvent.AddQuiz)
        }) {
            Text("Add Quiz")
        }

        val onInnerDialogCloseRequest = {
            onEvent(AddQuizScreenUiEvent.SetDialogState(AddQuizDialogState.None))
        }
        when (addQuizScreenState.addQuizDialogState) {
            is AddQuizDialogState.DateEventDialogState -> {
                SimpleDatePickerDialog(
                    onDateSelected = { selectedDate ->
                        onEvent(AddQuizScreenUiEvent.OnDateEventChange(selectedDate))
                    },
                    onDismissRequest = onInnerDialogCloseRequest,
                    initialSelectedDate = addQuizScreenState.eventDate
                )
            }

            is AddQuizDialogState.TimeEventDialogState -> {
                SimpleTimePickerDialog(
                    onTimeSelected = { selectedTime ->
                        onEvent(AddQuizScreenUiEvent.OnTimeEventChange(selectedTime))
                    },
                    onDismissRequest = onInnerDialogCloseRequest,
                    initialSelectedTime = addQuizScreenState.eventTime
                )
            }

            is AddQuizDialogState.DateRegDialogState -> {
                SimpleDatePickerDialog(
                    onDateSelected = { selectedDate ->
                        onEvent(AddQuizScreenUiEvent.OnDateRegChange(selectedDate))
                    },
                    onDismissRequest = onInnerDialogCloseRequest,
                    initialSelectedDate = addQuizScreenState.registrationDate
                )
            }
            is AddQuizDialogState.TimeRegDialogState -> {
                SimpleTimePickerDialog(
                    onTimeSelected = { selectedTime ->
                        onEvent(AddQuizScreenUiEvent.OnTimeRegChange(selectedTime))
                    },
                    onDismissRequest = onInnerDialogCloseRequest,
                    initialSelectedTime = addQuizScreenState.registrationTime
                )
            }

            else -> {}
        }
    }
}
