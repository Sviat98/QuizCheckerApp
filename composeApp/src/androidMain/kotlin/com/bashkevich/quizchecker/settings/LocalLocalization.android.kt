package com.bashkevich.quizchecker.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.bashkevich.quizchecker.settings.domain.SettingsLocale
import com.bashkevich.quizchecker.settings.domain.getDefaultSettingsLocale
import java.util.Locale as JavaLocale

actual object LocalLocalization {
    private var defaultLocale: SettingsLocale? = null

    actual val current: SettingsLocale
        @Composable
        get() = getDefaultSettingsLocale()

    @Composable
    actual infix fun provides(value: SettingsLocale?): ProvidedValue<*> {
        val configuration = LocalConfiguration.current

        if (defaultLocale == null) {
            defaultLocale = getDefaultSettingsLocale()
        }

        val newLocale = value ?: defaultLocale!!

        changeLocale(newLocale)
        val newJavaLocale = JavaLocale(newLocale.isoFormat, newLocale.country)
        configuration.setLocale(newJavaLocale)

        val context = LocalContext.current
        val newContext = context.createConfigurationContext(configuration)

        return LocalContext provides newContext
    }
}
