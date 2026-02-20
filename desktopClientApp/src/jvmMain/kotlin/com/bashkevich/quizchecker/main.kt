package com.bashkevich.quizchecker

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.bashkevich.quizchecker.adminApp.AdminApp
import com.bashkevich.quizchecker.clientApp.ClientApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "QuizCheckerApp",
    ) {
        ClientApp()
    }
}