package com.bashkevich.quizchecker.settings

import com.bashkevich.quizchecker.settings.domain.SettingsLocale

data class Locale(
    val language: String,
    val country: String
)

expect object LocaleBuilder {
    fun getDefault(): Locale
}

expect fun changeLocale(settingsLocale: SettingsLocale)

class NumberFormat(
    val locale: SettingsLocale,
    val fractionDigits: Int
)

expect fun NumberFormat.formatToDecimalString(number: Double): String

expect object NumberFormatBuilder {
    fun getNumberInstance(settingsLocale: SettingsLocale, fractionDigits: Int): NumberFormat
}
