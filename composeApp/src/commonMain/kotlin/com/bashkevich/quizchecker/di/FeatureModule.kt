package com.bashkevich.quizchecker.di

import com.bashkevich.quizchecker.adminApp.screens.addquiz.AddQuizViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.bashkevich.quizchecker.adminApp.screens.quizlist.QuizListViewModel
import org.koin.core.module.dsl.factoryOf

val quizListModule = module {
    viewModelOf(::QuizListViewModel)
}

val addQuizModule = module {
    factoryOf(::AddQuizViewModel)
}
val featureModule = module {
    includes(quizListModule, addQuizModule)
}

