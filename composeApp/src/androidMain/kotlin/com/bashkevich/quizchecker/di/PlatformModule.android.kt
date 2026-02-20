package com.bashkevich.quizchecker.di

import com.bashkevich.quizchecker.PlatformConfiguration
import org.koin.dsl.module

actual val platformModule = module {
    single {
        PlatformConfiguration(get())
    }
}