package com.bashkevich.quizchecker.core.ktor

import io.ktor.client.plugins.ResponseException

data class NetworkError(
    val errorCode: Int = -1,
    val name: String = "",
    val message: String = ""
)

fun Throwable.toNetworkError(): NetworkError {
    val errorCode = if (this is ResponseException) this.response.status.value else -1

    val name = this.cause.toString()
    val message = this.message ?: ""

    return  NetworkError(errorCode,name,message)
}