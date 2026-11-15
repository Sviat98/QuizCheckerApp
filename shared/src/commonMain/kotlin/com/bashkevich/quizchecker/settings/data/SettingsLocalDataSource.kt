package com.bashkevich.quizchecker.settings.data

import com.bashkevich.quizchecker.core.storage.KeyValueStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import com.bashkevich.quizchecker.settings.domain.LOCALES
import com.bashkevich.quizchecker.settings.domain.SettingsLocale

class SettingsLocalDataSource(
    private val keyValueStorage: KeyValueStorage
) {

    fun observeLocale(): Flow<SettingsLocale> =
        keyValueStorage.observeLocaleString()
            .distinctUntilChanged()
            .map { localeString ->
                parseLocaleString(localeString)
            }

    suspend fun setLocale(locale: SettingsLocale) {
        val localeString = "${locale.isoFormat}_${locale.country}"
        keyValueStorage.setLocaleString(localeString)
    }

    private fun parseLocaleString(localeString: String): SettingsLocale {
        val (language,country) = localeString.split('_')

        return LOCALES.firstOrNull { it.isoFormat == language && it.country == country }
                    ?: SettingsLocale.English_UK
    }
}
