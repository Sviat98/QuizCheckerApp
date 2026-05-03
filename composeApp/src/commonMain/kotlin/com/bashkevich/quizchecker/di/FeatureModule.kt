package com.bashkevich.quizchecker.di

import com.bashkevich.quizchecker.adminApp.AdminAppViewModel
import com.bashkevich.quizchecker.adminApp.screens.addquiz.AddQuizViewModel
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.QuizDetailsViewModel
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks.QuizDetailsBlanksViewModel
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.home.QuizDetailsHomeViewModel
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.standings.QuizDetailsStandingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.bashkevich.quizchecker.adminApp.screens.quizlist.QuizListViewModel as AdminQuizListViewModel
import com.bashkevich.quizchecker.clientApp.screens.home.HomeViewModel
import com.bashkevich.quizchecker.clientApp.screens.quizlist.QuizListViewModel as ClientQuizListViewModel
import org.koin.core.module.dsl.factoryOf
import com.bashkevich.quizchecker.adminApp.screens.settings.SettingsViewModel
import org.koin.core.module.dsl.singleOf

val quizListModule = module {
    viewModelOf(::AdminQuizListViewModel)
}

val clientQuizListModule = module {
    viewModelOf(::ClientQuizListViewModel)
}

val homeModule = module {
    viewModelOf(::HomeViewModel)
}

val quizDetailsModule = module {
    viewModelOf(::QuizDetailsViewModel)
    viewModelOf(::QuizDetailsHomeViewModel)
    viewModelOf(::QuizDetailsBlanksViewModel)
    viewModelOf(::QuizDetailsStandingsViewModel)
}

val addQuizModule = module {
    factoryOf(::AddQuizViewModel)
}

val settingsFeatureModule = module {
    viewModelOf(::SettingsViewModel)
}

val featureModule = module {
    includes(quizListModule, clientQuizListModule, homeModule, quizDetailsModule, addQuizModule, settingsFeatureModule)

    singleOf(::AdminAppViewModel)
}
