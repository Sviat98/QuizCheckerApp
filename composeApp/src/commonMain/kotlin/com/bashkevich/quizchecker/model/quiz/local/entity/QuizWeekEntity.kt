package com.bashkevich.quizchecker.model.quiz.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_week")
data class QuizWeekEntity(
    @PrimaryKey
    val id: String,
    val title: String
)
