package com.bashkevich.quizchecker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform