package com.bashkevich.quizchecker.settings

import com.bashkevich.quizchecker.settings.domain.SettingsLocale
import java.util.Locale as JavaLocale
import java.text.NumberFormat as JavaNumberFormat

actual object LocaleBuilder {
    actual fun getDefault(): Locale {
        val locale = JavaLocale.getDefault()

        return Locale(locale.language, locale.country)
    }
}

actual fun changeLocale(settingsLocale: SettingsLocale) {
    val locale = JavaLocale.of(settingsLocale.isoFormat, settingsLocale.country)
    JavaLocale.setDefault(locale)
}

actual fun NumberFormat.formatToDecimalString(number: Double): String {
    val locale = JavaLocale.of(this.locale.isoFormat, this.locale.country)
    val numberFormat = JavaNumberFormat.getNumberInstance(locale)
    numberFormat.minimumFractionDigits = this.fractionDigits
    numberFormat.maximumFractionDigits = this.fractionDigits

    return numberFormat.format(number)
}

actual object NumberFormatBuilder {
    actual fun getNumberInstance(
        settingsLocale: SettingsLocale,
        fractionDigits: Int
    ): NumberFormat {
        val locale = JavaLocale.of(settingsLocale.isoFormat, settingsLocale.country)
        val numberFormat = JavaNumberFormat.getNumberInstance(locale)
        numberFormat.minimumFractionDigits = fractionDigits
        numberFormat.maximumFractionDigits = fractionDigits

        return NumberFormat(settingsLocale, fractionDigits)
    }
}
