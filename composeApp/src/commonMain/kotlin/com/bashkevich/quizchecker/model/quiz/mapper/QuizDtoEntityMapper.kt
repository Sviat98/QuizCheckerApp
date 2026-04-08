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
    quiz_week_id = quizWeekId,
    quiz_day_id = this.id,
    season_number = this.seasonNumber,
    date_time = this.dateTime.toInstant(TimeZone.UTC).toEpochMilliseconds(),
    status = this.status.name,
    registration_open = this.registrationOpen,
    city = this.city,
    registration_date_time = this.registrationTimeBegin.toInstant(TimeZone.UTC).toEpochMilliseconds()
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
        id = this.quiz_day_id,
        dateTime = kotlinx.datetime.Instant.fromEpochMilliseconds(this.date_time)
            .toLocalDateTime(TimeZone.UTC),
        registrationTimeBegin = kotlinx.datetime.Instant.fromEpochMilliseconds(this.registration_date_time)
            .toLocalDateTime(TimeZone.UTC),
        city = this.city,
        status = Status.valueOf(this.status),
        registrationOpen = this.registration_open
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

//fun QuizEventEntity.toDomain(showRegisteredStatus: Boolean = false): Quiz {
//    val quizDay = this.quizDay
//    val status = Status.valueOf(quizDay.status)
//
//    return Quiz(
//        id = this.quizWeek.id,
//        title = this.quizWeek.title,
//        quizDay = QuizDay(
//            id = this.quizDay.quiz_day_id,
//            status = if (showRegisteredStatus && this.regFlag && status == Status.NOT_STARTED) Status.REGISTERED else status,
//            registrationOpen = quizDay.registration_open,
//            dateTime = kotlinx.datetime.Instant.fromEpochMilliseconds(quizDay.date_time)
//                .toLocalDateTime(TimeZone.UTC),
//            registrationTimeBegin = kotlinx.datetime.Instant.fromEpochMilliseconds(quizDay.registration_date_time)
//                .toLocalDateTime(TimeZone.UTC),
//            city = quizDay.city
//            )
//    )
//}
