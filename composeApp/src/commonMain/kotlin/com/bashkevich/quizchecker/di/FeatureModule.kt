package com.bashkevich.quizchecker.di

import com.bashkevich.quizchecker.adminApp.AdminAppViewModel
import com.bashkevich.quizchecker.adminApp.screens.addquiz.AddQuizViewModel
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.QuizDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.bashkevich.quizchecker.adminApp.screens.quizlist.QuizListViewModel
import org.koin.core.module.dsl.factoryOf
import com.bashkevich.quizchecker.adminApp.screens.settings.SettingsViewModel
import org.koin.core.module.dsl.singleOf

val quizListModule = module {
    viewModelOf(::QuizListViewModel)
}

val quizDetailsModule = module {
    viewModelOf(::QuizDetailsViewModel)
}

val addQuizModule = module {
    factoryOf(::AddQuizViewModel)
}

val settingsFeatureModule = module {
    viewModelOf(::SettingsViewModel)
}

val featureModule = module {
    includes(quizListModule, quizDetailsModule, addQuizModule, settingsFeatureModule)

    singleOf(::AdminAppViewModel)
}

