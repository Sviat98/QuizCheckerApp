package com.bashkevich.quizchecker

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.bashkevich.quizchecker.adminApp.AdminApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "QuizCheckerApp",
    ) {
        AdminApp()
    }
}