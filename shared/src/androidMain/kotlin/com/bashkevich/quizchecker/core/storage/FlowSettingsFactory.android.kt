package com.bashkevich.quizchecker.core.storage

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.bashkevich.quizchecker.PlatformConfiguration
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.datastore.DataStoreSettings


internal actual class FlowSettingsFactory actual constructor(private val platformConfiguration: PlatformConfiguration) {

    private val Context.datastore by preferencesDataStore("Football_wars")

    @OptIn(ExperimentalSettingsApi::class, ExperimentalSettingsImplementation::class)
    actual fun createSettings(): FlowSettings {

        val dataStore = platformConfiguration.androidContext.datastore
        return DataStoreSettings(dataStore)
    }
}
