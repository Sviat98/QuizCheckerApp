package com.bashkevich.quizchecker.di

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import com.bashkevich.quizchecker.QuizCheckerDatabase
import com.bashkevich.quizchecker.core.ktor.KtorHttpEngine
import com.bashkevich.quizchecker.core.sqldelight.DriverFactory
import com.bashkevich.quizchecker.core.storage.FlowSettingsFactory
import com.example.Quiz_day
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
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
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
    singleOf(::DriverFactory)
    single {
        val localDateTimeAdapter = object : ColumnAdapter<LocalDateTime, Long> {
            override fun decode(databaseValue: Long): LocalDateTime {
                return kotlin.time.Instant.fromEpochMilliseconds(databaseValue).toLocalDateTime(TimeZone.UTC)
            }

            override fun encode(value: LocalDateTime): Long {
                return value.toInstant(TimeZone.UTC).toEpochMilliseconds()
            }

        }

        val databaseDriverFactory: DriverFactory = get()
        QuizCheckerDatabase(
            databaseDriverFactory.createDriver(), quiz_dayAdapter = Quiz_day.Adapter(
                date_timeAdapter = localDateTimeAdapter,
                registration_date_timeAdapter = localDateTimeAdapter,
                statusAdapter = EnumColumnAdapter()
            )
        )
    }
}

@OptIn(ExperimentalSettingsApi::class)
val keyValueStorageModule = module {
    singleOf(::FlowSettingsFactory)
//    single {
//        val flowSettingsFactory: FlowSettingsFactory = get()
//        KeyValueStorage(flowSettingsFactory.createSettings())
//    }
}

val coreModule = module {
    includes(ktorModule,databaseModule)
}