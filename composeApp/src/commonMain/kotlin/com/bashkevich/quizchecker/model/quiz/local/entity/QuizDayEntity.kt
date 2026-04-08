package com.bashkevich.quizchecker.model.quiz.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "quiz_day",
    primaryKeys = ["quiz_week_id", "quiz_day_id"],
    foreignKeys = [
        ForeignKey(
            entity = QuizWeekEntity::class,
            parentColumns = ["id"],
            childColumns = ["quiz_week_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["quiz_week_id"]),
        Index(value = ["quiz_day_id"]),
        Index(value = ["date_time"])
    ]
)
data class QuizDayEntity(
    val quiz_week_id: String,
    val quiz_day_id: String,
    val season_number: Int,
    val date_time: Long,  // Stored as epoch milliseconds - no converter needed
    val status: String,   // Stored as String directly - no converter needed
    val registration_open: Boolean,
    val city: String,
    val registration_date_time: Long  // Stored as epoch milliseconds - no converter needed
)
