package com.bashkevich.quizchecker.model.blank_template.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answer_template")
data class AnswerTemplateEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("answer")
    val answer: String,
    @ColumnInfo("points")
    val points: Double
)
