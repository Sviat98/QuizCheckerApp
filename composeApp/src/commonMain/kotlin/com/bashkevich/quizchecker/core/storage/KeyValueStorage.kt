package com.bashkevich.quizchecker.core.storage

import com.bashkevich.quizchecker.settings.Locale
import com.bashkevich.quizchecker.settings.LocaleBuilder
import com.bashkevich.quizchecker.settings.domain.LOCALES
import com.bashkevich.quizchecker.settings.domain.SettingsLocale
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import kotlinx.coroutines.flow.Flow

class KeyValueStorage @OptIn(ExperimentalSettingsApi::class) constructor(
    private val flowSettings: FlowSettings
) {
    private val LOCALE_KEY = "locale"
    //private val THEME_KEY = "theme"

    private val locales = LOCALES.map { Locale(it.isoFormat, it.country) }

    @OptIn(ExperimentalSettingsApi::class)
    fun observeLocaleString(): Flow<String> {
        val defaultLocale = LocaleBuilder.getDefault()

        val defaultLocaleString = "${defaultLocale.language}_${defaultLocale.country}"

        val englishUKLocaleString = "${SettingsLocale.English_UK.isoFormat}_${SettingsLocale.English_UK.country}"
        val localeAtStart = if (defaultLocale in locales) defaultLocaleString else englishUKLocaleString
        return flowSettings.getStringFlow(LOCALE_KEY,localeAtStart)
    }

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun setLocaleString(localeString: String) {
        flowSettings.putString(LOCALE_KEY,localeString)
    }

   // @OptIn(ExperimentalSettingsApi::class)
//    suspend fun saveThemeMode(themeMode: ThemeMode){
//        flowSettings.putString(THEME_KEY,themeMode.name)
//    }
//
//    @OptIn(ExperimentalSettingsApi::class)
//    fun getThemeMode()= flowSettings.getStringFlow(THEME_KEY,ThemeMode.SYSTEM_DEFAULT.name)

}