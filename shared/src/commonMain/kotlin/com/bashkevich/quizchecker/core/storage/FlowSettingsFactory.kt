package com.bashkevich.quizchecker.core.storage

import com.bashkevich.quizchecker.PlatformConfiguration
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings

@OptIn(ExperimentalSettingsApi::class)
internal expect class FlowSettingsFactory(platformConfiguration: PlatformConfiguration) {
    fun createSettings(): FlowSettings
}
