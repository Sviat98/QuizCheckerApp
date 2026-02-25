package com.bashkevich.quizchecker.adminApp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bashkevich.quizchecker.adminApp.screens.addquiz.AddQuizScreen
import com.bashkevich.quizchecker.adminApp.screens.addquiz.AddQuizViewModel
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.QuizDetailsScreen
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.QuizDetailsViewModel
import com.bashkevich.quizchecker.adminApp.screens.quizlist.QuizListScreen
import com.bashkevich.quizchecker.adminApp.screens.quizlist.QuizListViewModel
import com.bashkevich.quizchecker.adminApp.screens.settings.SettingsScreen
import com.bashkevich.quizchecker.adminApp.screens.settings.SettingsViewModel
import com.bashkevich.quizchecker.di.coreModule
import com.bashkevich.quizchecker.di.featureModule
import com.bashkevich.quizchecker.di.platformModule
import com.bashkevich.quizchecker.di.quizModule
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration

val LocalNavHostController = staticCompositionLocalOf<NavHostController> {
    error("NavController not provided!")
}

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun AdminApp() {
    KoinMultiplatformApplication(config = KoinConfiguration {
        modules(platformModule, coreModule, quizModule, featureModule)
    })
    {
        MaterialTheme {
            val navController = rememberNavController()

            CompositionLocalProvider(
                LocalNavHostController provides navController
            ){
                NavHost(
                    navController = navController,
                    startDestination = QuizListRoute
                ) {
                    composable<QuizListRoute> {
                        val quizListViewModel = koinViewModel<QuizListViewModel>()

                        QuizListScreen(
                            modifier = Modifier.fillMaxSize(),
                            viewModel = quizListViewModel
                        )
                    }
                    composable<AddQuizRoute> {
                        val addQuizViewModel = koinViewModel<AddQuizViewModel>()

                        AddQuizScreen(
                            modifier = Modifier.fillMaxSize(),
                            viewModel = addQuizViewModel
                        )
                    }
                    composable<QuizDetailsRoute> {
                        val quizDetailsViewModel = koinViewModel<QuizDetailsViewModel>()

                        QuizDetailsScreen(
                            modifier = Modifier.fillMaxSize(),
                            viewModel = quizDetailsViewModel,
                        )
                    }
                    composable<SettingsRoute> {
                        val settingsViewModel = koinViewModel<SettingsViewModel>()

                        SettingsScreen(
                            modifier = Modifier.fillMaxSize(),
                            viewModel = settingsViewModel
                        )
                    }

                }
            }

        }
    }
}