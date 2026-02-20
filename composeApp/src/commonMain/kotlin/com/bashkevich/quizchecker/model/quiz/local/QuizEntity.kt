package com.bashkevich.quizchecker.model.quiz.local


import com.bashkevich.quizchecker.model.Status
import com.example.Quiz_day
import com.example.Quiz_week
import kotlinx.datetime.LocalDateTime

data class QuizWeekEntity(
    val id: String,
    val title: String,
)

data class QuizDayEntity(
    val quizWeekId: String,
    val quizDayId: String,
    val seasonNumber: Int,
    val dateTime: LocalDateTime,
    val status: Status,
    val city: String,
    val registrationDateTime: LocalDateTime
)

//data class QuizWeekWithQuizDay(
//    val quizDay: QuizDayEntity,
//    val quizWeekEntity: QuizWeekEntity
//
//)

data class QuizWeekWithQuizDay(
    val quizDay: Quiz_day,
    val quizWeekEntity: Quiz_week

)

data class QuizEventItemEntity(
    val quizDay: Quiz_day,
    val quizWeekEntity: Quiz_week,
    val regFlag: Boolean
)

data class QuizWeekWithQuizDays(
    val quizWeekEntity: Quiz_week,
    val quizDays: List<Quiz_day>
)

//data class QuizWeekWithQuizDays(
//    val quizWeekEntity: QuizWeekEntity,
//    val quizDays: List<QuizDayEntity>
//)