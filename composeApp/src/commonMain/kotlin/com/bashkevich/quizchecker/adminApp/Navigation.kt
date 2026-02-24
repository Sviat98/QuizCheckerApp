package com.bashkevich.quizchecker.adminApp

import kotlinx.serialization.Serializable

@Serializable
object QuizListRoute

@Serializable
data class QuizDetailsRoute(val quizId: String)

@Serializable
object AddQuizRoute