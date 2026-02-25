package com.bashkevich.quizchecker.settings.repository

import kotlinx.coroutines.flow.Flow
import com.bashkevich.quizchecker.settings.domain.SettingsLocale

interface SettingsRepository {
    fun observeLocale(): Flow<SettingsLocale>
    suspend fun setLocale(locale: SettingsLocale)
}
