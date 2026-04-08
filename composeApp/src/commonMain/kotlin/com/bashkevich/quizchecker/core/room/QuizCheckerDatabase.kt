package com.bashkevich.quizchecker.core.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.bashkevich.quizchecker.model.quiz.local.dao.QuizDao
import com.bashkevich.quizchecker.model.quiz.local.entity.PerformanceEntity
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizDayEntity
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizWeekEntity

@Database(
    entities = [
        QuizWeekEntity::class,
        QuizDayEntity::class,
        PerformanceEntity::class
    ],
    version = 1,
    exportSchema = true
)
@ConstructedBy(QuizCheckerDatabaseConstructor::class)
abstract class QuizCheckerDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object QuizCheckerDatabaseConstructor : RoomDatabaseConstructor<QuizCheckerDatabase> {
    override fun initialize(): QuizCheckerDatabase
}
