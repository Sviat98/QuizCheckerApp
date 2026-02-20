package com.bashkevich.quizchecker.di

import com.bashkevich.quizchecker.model.quiz.QuizRepository
import com.bashkevich.quizchecker.model.quiz.QuizRepositoryImpl
import com.bashkevich.quizchecker.model.quiz.local.QuizDao
import com.bashkevich.quizchecker.model.quiz.local.QuizLocalDataSource
import com.bashkevich.quizchecker.model.quiz.remote.QuizRemoteDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val quizModule = module {
    singleOf(::QuizRepositoryImpl){
        bind<QuizRepository>()
    }
    singleOf(::QuizRemoteDataSource)
    singleOf(::QuizLocalDataSource)
    singleOf(::QuizDao)
}