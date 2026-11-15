package com.bashkevich.quizchecker.core.room

import androidx.room.Room
import androidx.room.RoomDatabase
import com.bashkevich.quizchecker.PlatformConfiguration
import java.io.File

actual fun getDatabaseBuilder(configuration: PlatformConfiguration): RoomDatabase.Builder<QuizCheckerDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "quiz_checker.db")
    return Room.databaseBuilder<QuizCheckerDatabase>(
        name = dbFile.absolutePath
    )
}
