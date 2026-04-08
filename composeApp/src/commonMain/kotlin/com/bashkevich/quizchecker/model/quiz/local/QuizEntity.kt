package com.bashkevich.quizchecker.model.quiz.local

import androidx.room.Embedded
import androidx.room.Relation
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizDayEntity
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizWeekEntity

data class QuizEventEntity(
    @Embedded
    val quizWeek: QuizWeekEntity,
    @Embedded
    val quizDay: QuizDayEntity,
)

// One-to-many relationship: QuizWeek + List<QuizDay>
data class QuizWeekWithQuizDays(
    @Embedded
    val quizWeek: QuizWeekEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "quiz_week_id"
    )
    val quizDays: List<QuizDayEntity>
)
