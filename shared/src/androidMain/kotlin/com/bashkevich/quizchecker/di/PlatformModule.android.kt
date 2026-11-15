package com.bashkevich.quizchecker.di

import android.content.Context
import com.bashkevich.quizchecker.PlatformConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<PlatformConfiguration> {
        PlatformConfiguration(androidContext())
    }
}