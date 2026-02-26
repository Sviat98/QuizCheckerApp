package com.bashkevich.quizchecker.adminApp.screens.addquiz

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import com.bashkevich.quizchecker.composeapp.generated.resources.*
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
    initialSelectedDate: LocalDate? = null,
) {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDate?.atStartOfDayIn(TimeZone.UTC)
            ?.toEpochMilliseconds(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = Instant.fromEpochMilliseconds(utcTimeMillis)
                    .toLocalDateTime(TimeZone.UTC)
                    .date
                return selectedDate >= currentDate
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year >= currentDate.year
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.UTC)
                            .date
                        onDateSelected(localDate)
                    }
                    onDismissRequest()
                }
            ) {
                Text(stringResource(Res.string.ok_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(Res.string.cancel_button))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}