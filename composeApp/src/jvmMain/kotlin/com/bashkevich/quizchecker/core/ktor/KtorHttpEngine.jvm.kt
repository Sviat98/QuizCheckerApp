package com.bashkevich.quizchecker.core.ktor

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

internal actual class KtorHttpEngine actual constructor(){
    //actual val httpEngine = OkHttp

    actual fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
}