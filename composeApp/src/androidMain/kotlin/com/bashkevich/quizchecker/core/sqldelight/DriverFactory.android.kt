package com.bashkevich.quizchecker.core.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.bashkevich.quizchecker.PlatformConfiguration
import com.bashkevich.quizchecker.QuizCheckerDatabase


actual class DriverFactory actual constructor(private val platformConfiguration: PlatformConfiguration) {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = AndroidSqliteDriver(QuizCheckerDatabase.Schema, platformConfiguration.androidContext, "test.db")
        QuizCheckerDatabase.Schema.create(driver)
        return driver
    }
}