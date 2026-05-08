package com.bashkevich.quizchecker.model.quiz.remote

import com.bashkevich.quizchecker.model.Status
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizEventDto(
    val id: String,
    @SerialName(value = "season_number")
    val seasonNumber: Int = 0,
    @SerialName(value = "quiz_week")
    val quizWeek: QuizEventWeekDto,
    @Contextual
    @SerialName(value = "date_time")
    val dateTime: LocalDateTime,
    @SerialName(value = "status")
    val status: Status,
    @SerialName(value = "registration_open")
    val registrationOpen: Boolean,
    @SerialName(value = "city")
    val city: String,
    @Contextual
    @SerialName(value = "registration_time_begin")
    val registrationTimeBegin: LocalDateTime,
)

@Serializable
data class QuizEventWeekDto(
    @SerialName(value = "id")
    val id: String,
    @SerialName(value = "title")
    val title: String,
)

@Serializable
data class UpcomingQuizEventsResponse(
    @SerialName(value = "days_before_registration")
    val daysBeforeRegistration: Int,
    @SerialName(value = "quiz_events")
    val quizEvents: List<QuizEventDto>
)

@Serializable
data class AddQuizEventBody(
    @SerialName(value = "season_number")
    val seasonNumber: Int = 0,
    @SerialName(value = "quiz_week")
    val quizWeekTitle: String,
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