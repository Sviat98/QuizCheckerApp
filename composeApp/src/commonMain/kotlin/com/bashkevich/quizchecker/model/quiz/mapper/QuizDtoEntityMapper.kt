package com.bashkevich.quizchecker.model.quiz.mapper

import com.example.GetQuizEventById
import com.example.GetQuizList
import com.example.GetQuizSchedule
import com.example.Quiz_day
import com.example.Quiz_week
import com.bashkevich.quizchecker.model.quiz.domain.QuizDay
import com.bashkevich.quizchecker.model.quiz.local.QuizEventItemEntity
import com.bashkevich.quizchecker.model.quiz.local.QuizWeekWithQuizDay
import com.bashkevich.quizchecker.model.quiz.local.QuizWeekWithQuizDays
import com.bashkevich.quizchecker.model.quiz.remote.QuizDayDto
import com.bashkevich.quizchecker.model.quiz.remote.QuizWeekDto

fun QuizWeekDto.toSqlDelightEntity() = QuizWeekWithQuizDays(
    quizWeekEntity = Quiz_week(
        id = this.id,
        title = this.title
    ),
    quizDays = this.quizDays.map { quizDayDto -> quizDayDto.toSqlDelightEntity(this.id) }
)

//fun QuizWeekWithQuizDay.entityToDomain() =
//    Quiz(
//        id = this.quizWeekEntity.id,
//        title = this.quizWeekEntity.title,
//        quizDay = this.quizDay.entityToDomain()
//    )

//fun QuizWeekWithQuizDays.entityToDomain() =
//    Quiz(
//        id = this.quizWeekEntity.id,
//        title = this.quizWeekEntity.title,
//        quizDay = this.quizDays[0].entityToDomain()
//    )


fun Quiz_day.entityToDomain() =
    QuizDay(
        id = this.quiz_day_id,
        dateTime = this.date_time,
        registrationTimeBegin = this.registration_date_time,
        city = this.city,
        status = this.status,
        registrationOpen = this.registration_open
    )

fun GetQuizList.toEntity() = QuizWeekWithQuizDay(
    quizDay = Quiz_day(quiz_week_id, quiz_day_id, season_number, date_time, status,registration_open, city, registration_date_time),
    quizWeekEntity = Quiz_week(id = id,title)
)

fun GetQuizEventById.toEntity() = QuizWeekWithQuizDay(
    quizDay = Quiz_day(quiz_week_id, quiz_day_id, season_number, date_time, status,registration_open, city, registration_date_time),
    quizWeekEntity = Quiz_week(id = id,title)
)

fun GetQuizSchedule.toEntity() = QuizEventItemEntity(
    quizDay = Quiz_day(quiz_week_id, quiz_day_id, season_number, date_time, status,registration_open, city, registration_date_time),
    quizWeekEntity = Quiz_week(id = id,title),
    regFlag = this.reg_flag ==1L
)

//fun QuizWeekDto.entityToDomain() =
//    QuizWeek(
//        id = this.id,
//        seasonNumber = this.seasonNumber,
//        title = this.title,
//        quizDays = this.quizDays.map { it.entityToDomain() },
//    )

fun QuizDayDto.toSqlDelightEntity(quizWeekId: String) = Quiz_day(
    quiz_week_id = quizWeekId,
    quiz_day_id = this.id,
    city = this.city,
    status = this.status,
    //status = this.status.name,
    registration_open = this.registrationOpen,
    date_time = this.dateTime,
    registration_date_time = this.registrationTimeBegin,
    season_number = 0
)
