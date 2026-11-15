package com.bashkevich.quizchecker.core.room

import androidx.room.Room
import androidx.room.RoomDatabase
import com.bashkevich.quizchecker.PlatformConfiguration

actual fun getDatabaseBuilder(configuration: PlatformConfiguration): RoomDatabase.Builder<QuizCheckerDatabase> {
    val appContext = configuration.androidContext
    val dbFile = appContext.getDatabasePath("quiz_checker.db")
    return Room.databaseBuilder<QuizCheckerDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

