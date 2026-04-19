package com.bashkevich.quizchecker.core.room

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.bashkevich.quizchecker.PlatformConfiguration
import kotlinx.coroutines.Dispatchers

expect fun getDatabaseBuilder(configuration: PlatformConfiguration): RoomDatabase.Builder<QuizCheckerDatabase>

fun createRoomDatabase(builder: RoomDatabase.Builder<QuizCheckerDatabase>): QuizCheckerDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

