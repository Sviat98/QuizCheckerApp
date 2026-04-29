package com.bashkevich.quizchecker.core.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.bashkevich.quizchecker.model.blank_template.local.dao.BlankTemplateDao
import com.bashkevich.quizchecker.model.blank_template.local.entity.AnswerTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.BlankTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.SlotTemplateAnswerTemplateCrossRef
import com.bashkevich.quizchecker.model.blank_template.local.entity.SlotTemplateEntity
import com.bashkevich.quizchecker.model.quiz.local.dao.QuizDao
import com.bashkevich.quizchecker.model.quiz.local.entity.PerformanceEntity
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizDayEntity
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizWeekEntity

@Database(
    entities = [
        QuizWeekEntity::class,
        QuizDayEntity::class,
        PerformanceEntity::class,
        BlankTemplateEntity::class,
        SlotTemplateEntity::class,
        AnswerTemplateEntity::class,
        SlotTemplateAnswerTemplateCrossRef::class
    ],
    version = 2,
    exportSchema = true
)
@ConstructedBy(QuizCheckerDatabaseConstructor::class)
abstract class QuizCheckerDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
    abstract fun blankTemplateDao(): BlankTemplateDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object QuizCheckerDatabaseConstructor : RoomDatabaseConstructor<QuizCheckerDatabase> {
    override fun initialize(): QuizCheckerDatabase
}
