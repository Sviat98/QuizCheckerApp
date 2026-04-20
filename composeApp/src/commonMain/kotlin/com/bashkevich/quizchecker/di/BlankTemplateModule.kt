package com.bashkevich.quizchecker.di

import com.bashkevich.quizchecker.model.blank_template.BlankTemplateRepository
import com.bashkevich.quizchecker.model.blank_template.BlankTemplateRepositoryImpl
import com.bashkevich.quizchecker.model.blank_template.remote.BlankTemplateRemoteDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val blankTemplateModule = module {
    singleOf(::BlankTemplateRemoteDataSource)
    singleOf(::BlankTemplateRepositoryImpl) {
        bind<BlankTemplateRepository>()
    }
}
