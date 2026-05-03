package com.bashkevich.quizchecker.clientApp

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bashkevich.quizchecker.clientApp.screens.blanks.BlanksScreen
import com.bashkevich.quizchecker.clientApp.screens.home.HomeScreen
import com.bashkevich.quizchecker.clientApp.screens.home.HomeViewModel
import com.bashkevich.quizchecker.clientApp.screens.quizlist.QuizListScreen
import com.bashkevich.quizchecker.clientApp.screens.quizlist.QuizListViewModel
import com.bashkevich.quizchecker.clientApp.screens.settings.SettingsScreen
import com.bashkevich.quizchecker.clientApp.screens.standings.StandingsScreen
import com.bashkevich.quizchecker.components.icons.IconGroup
import com.bashkevich.quizchecker.components.icons.default_icons.Blanks
import com.bashkevich.quizchecker.components.icons.default_icons.Home
import com.bashkevich.quizchecker.components.icons.default_icons.Settings
import com.bashkevich.quizchecker.components.icons.default_icons.Trophy
import com.bashkevich.quizchecker.di.coreModule
import com.bashkevich.quizchecker.di.platformModule
import com.bashkevich.quizchecker.di.featureModule
import com.bashkevich.quizchecker.di.quizModule
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration

private val bottomNavRoutes = setOf(
    HomeRoute::class.qualifiedName,
    BlanksRoute::class.qualifiedName,
    StandingsRoute::class.qualifiedName,
    SettingsRoute::class.qualifiedName,
)

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun ClientApp() {
    KoinMultiplatformApplication(config = KoinConfiguration {
        modules(coreModule, platformModule, quizModule, featureModule)
    }) {
        val navController = rememberNavController()

        ClientAppScreen(navController)
    }
}

@Composable
private fun ClientAppScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomNavRoutes

    val bottomNavItems = listOf(
        HomeRoute to "Home",
        BlanksRoute to "Blanks",
        StandingsRoute to "Standings",
        SettingsRoute to "Settings"
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { (route, label) ->
                        NavigationBarItem(
                            selected = currentRoute == route::class.qualifiedName,
                            onClick = {
                                navController.navigate(route) {
                                    popUpTo(HomeRoute) { inclusive = false }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = when (route) {
                                        HomeRoute -> IconGroup.Default.Home
                                        BlanksRoute -> IconGroup.Default.Blanks
                                        StandingsRoute -> IconGroup.Default.Trophy
                                        SettingsRoute -> IconGroup.Default.Settings
                                        else -> IconGroup.Default.Home
                                    },
                                    contentDescription = label
                                )
                            },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute
        ) {
            composable<HomeRoute> {
                val homeViewModel = koinViewModel<HomeViewModel>()
                HomeScreen(
                    viewModel = homeViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .background(Color.Red)
                    ,
                    onEventListClick = {
                        navController.navigate(QuizListRoute)
                    }
                )
            }
            composable<BlanksRoute> {
                BlanksScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                )
            }
            composable<StandingsRoute> {
                StandingsScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                )
            }
            composable<SettingsRoute> {
                SettingsScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                )
            }
            composable<QuizListRoute>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(300)
                    )
                },
            ) {
                val quizListViewModel = koinViewModel<QuizListViewModel>()
                QuizListScreen(
                    modifier = Modifier.fillMaxSize().background(Color.Blue),
                    viewModel = quizListViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
