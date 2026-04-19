package com.bashkevich.quizchecker.di

import com.bashkevich.quizchecker.core.ktor.KtorHttpEngine
import com.bashkevich.quizchecker.core.storage.FlowSettingsFactory
import com.bashkevich.quizchecker.core.storage.KeyValueStorage
import com.bashkevich.quizchecker.core.room.QuizCheckerDatabase
import com.bashkevich.quizchecker.core.room.createRoomDatabase
import com.bashkevich.quizchecker.core.room.getDatabaseBuilder
import com.bashkevich.quizchecker.settings.data.SettingsLocalDataSource
import com.bashkevich.quizchecker.settings.repository.SettingsRepository
import com.bashkevich.quizchecker.settings.repository.SettingsRepositoryImpl
import com.russhwolf.settings.ExperimentalSettingsApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val ktorModule = module {
    single {
        HttpClient(KtorHttpEngine().createEngine()) {
            expectSuccess = true

            defaultRequest {
                url("https://footballwarsadmin.onrender.com")
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 15000
                requestTimeoutMillis = 30000
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true

                },
                )
//                json(
//                    contentType = ContentType.Text.Plain
//                )
            }


            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }
}

val databaseModule = module {
    single {
        val configuration = get<com.bashkevich.quizchecker.PlatformConfiguration>()
        val builder = getDatabaseBuilder(configuration)
        createRoomDatabase(builder)
    }
    single {
        val database: QuizCheckerDatabase = get()
        database.quizDao()
    }
}

@OptIn(ExperimentalSettingsApi::class)
val keyValueStorageModule = module {
    singleOf(::FlowSettingsFactory)
    single {
        val flowSettingsFactory: FlowSettingsFactory = get()
        KeyValueStorage(flowSettingsFactory.createSettings())
    }
}

val coreModule = module {
    includes(ktorModule, databaseModule, keyValueStorageModule)

    singleOf(::SettingsLocalDataSource)
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}