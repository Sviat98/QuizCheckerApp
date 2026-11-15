package com.bashkevich.quizchecker.model.blank_template.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizWeekEntity

@Entity(
    tableName = "blank_template",
    foreignKeys = [
        ForeignKey(
            entity = QuizWeekEntity::class,
            parentColumns = ["id"],
            childColumns = ["quiz_week_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("quiz_week_id")]
)
data class BlankTemplateEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("quiz_week_id")
    val quizWeekId: String,
    @ColumnInfo("round_number")
    val roundNumber: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("slots_amount")
    val slotsAmount: Int
)
