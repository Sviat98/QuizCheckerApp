package com.bashkevich.quizchecker.model.quiz.local.converters

import androidx.room.TypeConverter
import com.bashkevich.quizchecker.model.Status

class RoomConverters {

    @TypeConverter
    fun fromStatus(status: Status): String {
        return status.name
    }

    @TypeConverter
    fun toStatus(value: String): Status {
        return try {
            Status.valueOf(value)
        } catch (e: IllegalArgumentException) {
            Status.NOT_STARTED  // Default value
        }
    }
}
