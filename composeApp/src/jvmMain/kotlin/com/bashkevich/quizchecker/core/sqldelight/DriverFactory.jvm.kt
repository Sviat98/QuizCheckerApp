package com.bashkevich.quizchecker.core.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bashkevich.quizchecker.PlatformConfiguration
import com.bashkevich.quizchecker.QuizCheckerDatabase

actual class DriverFactory actual constructor(private val platformConfiguration: PlatformConfiguration) {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        QuizCheckerDatabase.Schema.create(driver)
        return driver
    }
}