package com.bashkevich.quizchecker.model.quiz.remote

import com.bashkevich.quizchecker.model.Status
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizEventDto(
    @SerialName(value = "id")
    val id: String? = null,
//    @SerialName(value = "season_number")
//    val seasonNumber: Int = 0,
    @SerialName(value = "title")
    val title: String,
    @SerialName(value = "quiz_day")
    val quizDay: QuizDayDto
)

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

@Serializable
data class UpcomingQuizWeeksResponse(
    @SerialName(value = "days_before_registration")
    val daysBeforeRegistration: Int,
    @SerialName(value = "quiz_events")
    val quizEvents: List<QuizEventDto>
)