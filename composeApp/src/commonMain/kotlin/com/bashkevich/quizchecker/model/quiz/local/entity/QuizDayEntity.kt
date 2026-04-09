package com.bashkevich.quizchecker.model.quiz.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "quiz_day",
    foreignKeys = [
        ForeignKey(
            entity = QuizWeekEntity::class,
            parentColumns = ["id"],
            childColumns = ["quiz_week_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class QuizDayEntity(
    @PrimaryKey
    @ColumnInfo("quiz_day_id")
    val quizDayId: String,
    @ColumnInfo("quiz_week_id")
    val quizWeekId: String,
    @ColumnInfo("season_number")
    val seasonNumber: Int,
    @ColumnInfo("date_time")
    val dateTime: Long,
    @ColumnInfo("status")
    val status: String,
    @ColumnInfo("registration_open")
    val registrationOpen: Boolean,
    @ColumnInfo("city")
    val city: String,
    @ColumnInfo("registration_date_time")
    val registrationDateTime: Long
)
