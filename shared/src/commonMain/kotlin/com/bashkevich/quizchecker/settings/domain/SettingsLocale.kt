package com.bashkevich.quizchecker.settings.domain

import com.bashkevich.quizchecker.settings.LocaleBuilder

sealed class SettingsLocale(val isoFormat: String, val country: String, val label: String) {

    data object English_US : SettingsLocale("en", "US", "English (US)")
    data object English_UK : SettingsLocale("en", "UK", "English (UK)")
    data object Russian_RU : SettingsLocale("ru", "RU", "Русский (Россия)")
    data object German_DE : SettingsLocale("de", "DE", "Deutsch (Deutschland)")
}

val LOCALES = listOf(
    SettingsLocale.English_UK,
    SettingsLocale.English_US,
    SettingsLocale.Russian_RU,
    SettingsLocale.German_DE
)

fun getDefaultSettingsLocale(): SettingsLocale {
    val defaultLocale = LocaleBuilder.getDefault()

    val defaultSettingsLocale =
        LOCALES.firstOrNull { it.isoFormat == defaultLocale.language && it.country == defaultLocale.country }

    return defaultSettingsLocale ?: SettingsLocale.English_UK
}
