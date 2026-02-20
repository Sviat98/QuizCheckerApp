package com.bashkevich.quizchecker.model.quiz.mapper

import com.bashkevich.quizchecker.model.Status
import com.example.Quiz_week
import com.bashkevich.quizchecker.model.quiz.domain.Quiz
import com.bashkevich.quizchecker.model.quiz.domain.QuizDay
import com.bashkevich.quizchecker.model.quiz.local.QuizEventItemEntity
import com.bashkevich.quizchecker.model.quiz.local.QuizWeekWithQuizDay
import com.bashkevich.quizchecker.model.quiz.local.QuizWeekWithQuizDays
import com.bashkevich.quizchecker.model.quiz.remote.QuizDayDto
import com.bashkevich.quizchecker.model.quiz.remote.QuizEventDto
import com.bashkevich.quizchecker.model.quiz.remote.QuizWeekDto

fun QuizWeekDto.toEntity() = QuizWeekWithQuizDays(
    quizWeekEntity = Quiz_week(
        id = this.id,
        title = this.title
    ),
    quizDays = this.quizDays.map { quizDayDto -> quizDayDto.toSqlDelightEntity(this.id) }
)

fun QuizWeekWithQuizDay.entityToDomain() =
    Quiz(
        id = this.quizWeekEntity.id,
        title = this.quizWeekEntity.title,
        quizDay = this.quizDay.entityToDomain()
    )

fun QuizWeekWithQuizDays.entityToDomain() =
    Quiz(
        id = this.quizWeekEntity.id,
        title = this.quizWeekEntity.title,
        quizDay = this.quizDays[0].entityToDomain()
    )


//fun QuizDayEntity.entityToDomain() =
//    QuizDay(
//        id = this.quizDayId,
//        dateTime = this.dateTime,
//        registrationTimeBegin = this.registrationDateTime,
//        city = this.city,
//        status = this.status,
//    )

fun QuizEventDto.toEntity() = QuizWeekWithQuizDay(
    quizWeekEntity = Quiz_week(
        id = this.id!!,
        title = this.title
    ),
    quizDay = this.quizDay.toSqlDelightEntity(this.id)
)

//fun QuizDayDto.toEntity(quizWeekId: String) = QuizDayEntity(
//    quizWeekId = quizWeekId,
//    quizDayId = this.id,
//    city = this.city,
//    status = this.status,
//    dateTime = this.dateTime,
//    registrationDateTime = this.registrationTimeBegin,
//    seasonNumber = 0
//)

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

//Статус REGISTERED нужен только для отображения в расписании на QuizListScreen(QuizScheduleScreen)

fun QuizEventItemEntity.toDomain(showRegisteredStatus: Boolean = false): Quiz {
    val quizDay = this.quizDay

    return Quiz(
        id = this.quizWeekEntity.id,
        title = this.quizWeekEntity.title,
        quizDay = QuizDay(
            id = this.quizDay.quiz_day_id,
            status = if (showRegisteredStatus && this.regFlag && quizDay.status == Status.NOT_STARTED) Status.REGISTERED else quizDay.status,
            registrationOpen = quizDay.registration_open,
            dateTime = quizDay.date_time,
            registrationTimeBegin = quizDay.registration_date_time,
            city = quizDay.city
            )
    )
}
