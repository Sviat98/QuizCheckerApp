package com.bashkevich.quizchecker.core.sqldelight

import app.cash.sqldelight.db.SqlDriver
import com.bashkevich.quizchecker.PlatformConfiguration

expect class DriverFactory(platformConfiguration: PlatformConfiguration) {
    fun createDriver(): SqlDriver
}

