package com.bashkevich.quizchecker.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import com.bashkevich.quizchecker.settings.domain.SettingsLocale

expect object LocalLocalization {
    val current: SettingsLocale @Composable get

    @Composable
    infix fun provides(value: SettingsLocale?): ProvidedValue<*>
}
