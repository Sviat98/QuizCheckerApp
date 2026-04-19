package com.bashkevich.quizchecker.model.quiz.mapper

import com.bashkevich.quizchecker.model.Status
import com.bashkevich.quizchecker.model.quiz.domain.Quiz
import com.bashkevich.quizchecker.model.quiz.domain.QuizDay
import com.bashkevich.quizchecker.model.quiz.local.QuizEventEntity
import com.bashkevich.quizchecker.model.quiz.local.QuizWeekWithQuizDays
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizDayEntity
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizWeekEntity
import com.bashkevich.quizchecker.model.quiz.remote.QuizDayDto
import com.bashkevich.quizchecker.model.quiz.remote.QuizEventDto
import com.bashkevich.quizchecker.model.quiz.remote.QuizWeekDto
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

// DTO -> Entity mapping functions

fun QuizWeekDto.toEntity() = QuizWeekWithQuizDays(
    quizWeek = QuizWeekEntity(
        id = this.id,
        title = this.title
    ),
    quizDays = this.quizDays.map { quizDayDto -> quizDayDto.toEntity(this.id) }
)

fun QuizEventDto.toEntity() = QuizEventEntity(
    quizWeek = QuizWeekEntity(
        id = this.id!!,
        title = this.title
    ),
    quizDay = this.quizDay.toEntity(this.id)
)

fun QuizDayDto.toEntity(quizWeekId: String) = QuizDayEntity(
    quizWeekId = quizWeekId,
    quizDayId = this.id,
    seasonNumber = this.seasonNumber,
    dateTime = this.dateTime.toInstant(TimeZone.UTC).toEpochMilliseconds(),
    status = this.status.name,
    registrationOpen = this.registrationOpen,
    city = this.city,
    registrationDateTime = this.registrationTimeBegin.toInstant(TimeZone.UTC).toEpochMilliseconds()
)

// Entity -> Domain mapping functions

//fun QuizWeekWithQuizDay.toDomain() =
//    Quiz(
//        id = this.quizWeek.id,
//        title = this.quizWeek.title,
//        quizDay = this.quizDay.toDomain()
//    )

fun QuizWeekWithQuizDays.toDomain() =
    Quiz(
        id = this.quizWeek.id,
        title = this.quizWeek.title,
        quizDay = this.quizDays[0].toDomain()
    )

fun QuizDayEntity.toDomain() =
    QuizDay(
        id = this.quizDayId,
        dateTime = kotlinx.datetime.Instant.fromEpochMilliseconds(this.dateTime)
            .toLocalDateTime(TimeZone.UTC),
        registrationTimeBegin = kotlinx.datetime.Instant.fromEpochMilliseconds(this.registrationDateTime)
            .toLocalDateTime(TimeZone.UTC),
        city = this.city,
        status = Status.valueOf(this.status),
        registrationOpen = this.registrationOpen
    )

// DTO -> Domain mapping (for direct conversion without database)

fun QuizEventDto.dtoToDomain() = Quiz(
    id = this.id!!,
    title = this.title,
    quizDay = this.quizDay.dtoToDomain(this.id)
)

fun QuizDayDto.dtoToDomain(quizWeekId: String) = QuizDay(
    id = quizWeekId,
    city = this.city,
    status = this.status,
    registrationOpen = this.registrationOpen,
    dateTime = this.dateTime,
    registrationTimeBegin = this.registrationTimeBegin,
    seasonNumber = 0
)

// QuizEventItemEntity -> Domain (for schedule with registration flag)
// Статус REGISTERED нужен только для отображения в расписании на QuizListScreen(QuizScheduleScreen)

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
            dateTime = Instant.fromEpochMilliseconds(quizDay.dateTime)
                .toLocalDateTime(TimeZone.UTC),
            registrationTimeBegin = Instant.fromEpochMilliseconds(quizDay.registrationDateTime)
                .toLocalDateTime(TimeZone.UTC),
            city = quizDay.city
            )
    )
}
