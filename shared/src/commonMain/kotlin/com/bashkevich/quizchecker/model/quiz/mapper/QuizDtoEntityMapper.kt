package com.bashkevich.quizchecker.model.quiz.mapper

import com.bashkevich.quizchecker.model.Status
import com.bashkevich.quizchecker.model.quiz.domain.Quiz
import com.bashkevich.quizchecker.model.quiz.domain.QuizDay
import com.bashkevich.quizchecker.model.quiz.local.QuizEventEntity
import com.bashkevich.quizchecker.model.quiz.local.QuizWeekWithQuizDays
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizDayEntity
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizWeekEntity
import com.bashkevich.quizchecker.model.quiz.remote.QuizEventDto
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

// DTO -> Entity mapping functions

fun QuizEventDto.toEntity() = QuizEventEntity(
    quizWeek = QuizWeekEntity(
        id = this.quizWeek.id,
        title = this.quizWeek.title
    ),
    quizDay = QuizDayEntity(
        quizWeekId = this.quizWeek.id,
        quizDayId = this.id,
        seasonNumber = this.seasonNumber,
        dateTime = this.dateTime.toInstant(TimeZone.UTC).toEpochMilliseconds(),
        status = this.status.name,
        registrationOpen = this.registrationOpen,
        city = this.city,
        registrationDateTime = this.registrationTimeBegin.toInstant(TimeZone.UTC).toEpochMilliseconds()
    )
)

// Entity -> Domain mapping functions

fun QuizWeekWithQuizDays.toDomain() =
    Quiz(
        id = this.quizWeek.id,
        title = this.quizWeek.title,
        quizDay = this.quizDays[0].toDomain()
    )

fun QuizDayEntity.toDomain() =
    QuizDay(
        id = this.quizDayId,
        dateTime = Instant.fromEpochMilliseconds(this.dateTime)
            .toLocalDateTime(TimeZone.UTC),
        registrationTimeBegin = kotlinx.datetime.Instant.fromEpochMilliseconds(this.registrationDateTime)
            .toLocalDateTime(TimeZone.UTC),
        city = this.city,
        status = Status.valueOf(this.status),
        registrationOpen = this.registrationOpen
    )

// DTO -> Domain mapping (for direct conversion without database)

fun QuizEventDto.dtoToDomain() = Quiz(
    id = this.quizWeek.id,
    title = this.quizWeek.title,
    quizDay = QuizDay(
        id = this.id,
        city = this.city,
        status = this.status,
        registrationOpen = this.registrationOpen,
        dateTime = this.dateTime,
        registrationTimeBegin = this.registrationTimeBegin,
        seasonNumber = this.seasonNumber
    )
)

// QuizEventEntity -> Domain

fun QuizEventEntity.entityToDomain(): Quiz {
    val quizDay = this.quizDay
    val status = Status.valueOf(quizDay.status)

    return Quiz(
        id = this.quizWeek.id,
        title = this.quizWeek.title,
        quizDay = QuizDay(
            id = this.quizDay.quizDayId,
            status = status,
            registrationOpen = quizDay.registrationOpen,
            dateTime = kotlinx.datetime.Instant.fromEpochMilliseconds(quizDay.dateTime)
                .toLocalDateTime(TimeZone.UTC),
            registrationTimeBegin = kotlinx.datetime.Instant.fromEpochMilliseconds(quizDay.registrationDateTime)
                .toLocalDateTime(TimeZone.UTC),
            city = quizDay.city
            )
    )
}
