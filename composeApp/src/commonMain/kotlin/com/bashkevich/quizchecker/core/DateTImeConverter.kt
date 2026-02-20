package com.bashkevich.quizchecker.core

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.convertToLocalTimeFromUTC() = this.toInstant(
    TimeZone.UTC
).toLocalDateTime(TimeZone.currentSystemDefault()).formatDateTime()



fun LocalDateTime.convertToUTCFromLocalTime() = this.toInstant(
    TimeZone.currentSystemDefault()
).toLocalDateTime(TimeZone.UTC)

fun LocalDateTime.formatDateTime() =
    "${day.toString().padStart(2, '0')}.${
        month.number.toString().padStart(2, '0')
    }.${this.year} ${this.hour.toString().padStart(2, '0')}:${this.minute.toString().padStart(2, '0')}"

fun LocalDate?.formatDate() = if (this!=null )
    "${this.day.toString().padStart(2, '0')}.${
        this.month.number.toString().padStart(2, '0')
    }.${this.year}" else  ""

fun LocalTime?.formatTime() = if (this!=null )
    "${this.hour.toString().padStart(2, '0')}:${this.minute.toString().padStart(2, '0')}" else  ""