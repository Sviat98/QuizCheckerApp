package com.bashkevich.quizchecker.core.ktor

import io.ktor.client.engine.*

internal expect class KtorHttpEngine constructor(){
    //expect val httpEngine:HttpClientEngine

   fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig>
}