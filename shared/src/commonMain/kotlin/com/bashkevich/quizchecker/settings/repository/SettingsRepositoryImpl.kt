package com.bashkevich.quizchecker.settings.repository

import kotlinx.coroutines.flow.Flow
import com.bashkevich.quizchecker.settings.data.SettingsLocalDataSource
import com.bashkevich.quizchecker.settings.domain.SettingsLocale

class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override fun observeLocale(): Flow<SettingsLocale> =
        settingsLocalDataSource.observeLocale()

    override suspend fun setLocale(locale: SettingsLocale) {
        settingsLocalDataSource.setLocale(locale)
    }
}
