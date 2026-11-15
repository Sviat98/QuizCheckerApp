package com.bashkevich.quizchecker.di

import com.bashkevich.quizchecker.PlatformConfiguration
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


actual val platformModule = module {
    singleOf(::PlatformConfiguration)
}