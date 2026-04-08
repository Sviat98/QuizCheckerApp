package com.bashkevich.quizchecker.model.quiz.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "performance_entity")
data class PerformanceEntity(
    @PrimaryKey
    val id: String,
    val quiz_day_id: String,
    val team_name: String,
    val started: Boolean,
    val points: Float,
    val rank: Int
)
