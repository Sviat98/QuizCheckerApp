package com.bashkevich.quizchecker.core.ktor

import io.ktor.client.engine.*
import io.ktor.client.engine.android.*

internal actual class KtorHttpEngine actual constructor(){
    //actual val httpEngine = Android

    actual fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = Android
}