package com.bashkevich.quizchecker.model.quiz.remote

import com.bashkevich.quizchecker.model.Status
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Clock
@Serializable
data class QuizWeekDto(
    @SerialName(value = "id")
    val id: String,
    @SerialName(value = "season_number")
    val seasonNumber: Int = 0,
    @SerialName(value = "title")
    val title: String,
    @SerialName(value = "quiz_days")
    val quizDays: List<QuizDayDto>,
)


@Serializable
data class QuizDayDto(
    val id: String,
    @SerialName(value = "season_number")
    val seasonNumber: Int = 0,
    @SerialName(value = "date_time")
    val dateTime: LocalDateTime,
    @SerialName(value = "status")
    val status: Status,
    @SerialName(value = "registration_open")
    val registrationOpen: Boolean,
    @SerialName(value = "city")
    val city: String,
    @SerialName(value = "registration_time_begin")
    val registrationTimeBegin: LocalDateTime,
)