package com.bashkevich.quizchecker.model.quiz.domain

import com.bashkevich.quizchecker.model.Status
import kotlin.time.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

//val DEFAULT
//    get() = Quiz(
//         "", "", QuizDay(
//             "", 0, Status.NOT_STARTED, false, Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
//             Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
//             ""
//         )
//     )

data class Quiz(
    val id: String ,
    val title: String,
    val quizDay: QuizDay,
){
    companion object{
        val DEFAULT = Quiz(
                "", "", QuizDay(
                    "", 0, Status.NOT_STARTED, false, LocalDateTime(2023,1,1,0,0),
                    LocalDateTime(2023,1,1,0,0),
                    ""
            )
            )
    }
}


data class QuizDay(
    val id: String,
    val seasonNumber: Int = 0,
    val status: Status,
    val registrationOpen: Boolean,
    val dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val registrationTimeBegin: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val city: String,
)



