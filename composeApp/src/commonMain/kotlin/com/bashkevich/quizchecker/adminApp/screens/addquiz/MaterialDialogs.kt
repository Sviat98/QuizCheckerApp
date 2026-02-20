package com.bashkevich.quizchecker.adminApp.screens.addquiz

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime



//@OptIn(ExperimentalTime::class)
//@Composable
//private fun DatePickerDialog(
//    onCloseRequest: () -> Unit,
//    dialogState: MaterialDialogState,
//    initialDate: LocalDate,
//    dateValidator: (LocalDate)->Boolean,
//    onDateChange: (LocalDate) -> Unit,
//) {
//    val datePickerState = rememberDatePickerState()
//    MaterialDialog(
//        dialogState = dialogState,
//        onCloseRequest = {
//            it.hide()
//            onCloseRequest()
//        },
//        properties = MaterialDialogProperties(windowSize = DpSize(width = 400.dp, height = 500.dp)),
//        buttons = {
//            positiveButton("Ok")
//            negativeButton("Cancel")
//        }
//    ) {
//
//
//        datepicker(
//            initialDate = initialDate,
//            allowedDateValidator = dateValidator
//        ) { selectedDate ->
//
//            onDateChange(selectedDate)
//
//            //
//            // Do stuff with java.time.LocalDate object which is passed in
//        }
//    }
//}
//
//@OptIn(ExperimentalTime::class)
//@Composable
//private fun TimePickerDialog(
//    onCloseRequest: () -> Unit,
//    initialTime: LocalTime,
//    dialogState: MaterialDialogState,
//    onTimeChange: (LocalTime) -> Unit
//) {
//    //val datePickerDialogState = rememberMaterialDialogState()
//
//    MaterialDialog(
//        dialogState = dialogState,
//        onCloseRequest = {
//            it.hide()
//            onCloseRequest()
//        },
//        properties = MaterialDialogProperties(windowSize = DpSize(width = 400.dp, height = 500.dp)),
//        buttons = {
//            positiveButton("Ok")
//            negativeButton("Cancel")
//        }
//    ) {
//
//
//        timepicker(
//            initialTime = initialTime,
//            is24HourClock = true
//        ) { selectedTime ->
//
//            onTimeChange(selectedTime)
//
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDatePickerDialog(
    onCloseRequest: () -> Unit,
//    initialDate: LocalDate,
//    onDateChange: (LocalDate) -> Unit
) {
    val eventDatePickerDialogState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds()
    )


    DatePickerDialog(
        onDismissRequest = onCloseRequest,
        confirmButton = {Text("Ok")}
    ){
        DatePicker(
            state = eventDatePickerDialogState
        )
    }
}

//@Composable
//fun EventTimePickerDialog(
//    onCloseRequest: () -> Unit,
//    initialTime: LocalTime,
//    onTimeChange: (LocalTime) -> Unit
//) {
//
//    val eventTimePickerDialogState = rememberMaterialDialogState(true)
//
//    TimePickerDialog(
//        onCloseRequest = onCloseRequest,
//        dialogState = eventTimePickerDialogState,
//        initialTime = initialTime,
//        onTimeChange = onTimeChange,
//    )
//}
//
//@Composable
//fun RegistrationDatePickerDialog(
//    onCloseRequest: () -> Unit,
//    initialDate: LocalDate,
//    onDateChange: (LocalDate) -> Unit
//) {
//    val registrationDatePickerDialogState = rememberMaterialDialogState(true)
//
//    DatePickerDialog(
//        onCloseRequest = onCloseRequest,
//        dialogState = registrationDatePickerDialogState,
//        initialDate  =initialDate,
//        dateValidator = {date->
//            val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
//
//            (date >= currentDate) && (date<=currentDate.plus(DatePeriod(days = 30)))
//        },
//        onDateChange = onDateChange,
//    )
//}
//
//@Composable
//fun RegistrationTimePickerDialog(
//    onCloseRequest: () -> Unit,
//    initialTime: LocalTime,
//    onTimeChange: (LocalTime) -> Unit
//) {
//    val registrationTimePickerDialogState = rememberMaterialDialogState(true)
//
//    TimePickerDialog(
//        onCloseRequest = onCloseRequest,
//        dialogState = registrationTimePickerDialogState,
//        initialTime = initialTime,
//        onTimeChange = onTimeChange,
//    )
//}