package com.bashkevich.quizchecker.adminApp.screens.addquiz

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTimePickerDialog(
    onTimeSelected: (LocalTime) -> Unit,
    onDismissRequest: () -> Unit,
    initialSelectedTime: LocalTime? = null,
) {
    val localTine = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val timePickerState = rememberTimePickerState(
        initialHour = initialSelectedTime?.hour ?: localTine.hour,
        initialMinute = initialSelectedTime?.minute ?: localTine.minute,
        is24Hour = true
    )

    TimePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    val localTime =
                        LocalTime(hour = timePickerState.hour, minute = timePickerState.minute)
                    onTimeSelected(localTime)
                    onDismissRequest()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        title = { Text("Select time") }
    ) {
        TimePicker(state = timePickerState)
    }
}
