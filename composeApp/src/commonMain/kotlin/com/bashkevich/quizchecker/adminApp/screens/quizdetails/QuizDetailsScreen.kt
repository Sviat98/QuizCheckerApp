package com.bashkevich.quizchecker.adminApp.screens.quizdetails

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bashkevich.quizchecker.adminApp.LocalNavHostController
import com.bashkevich.quizchecker.adminApp.SettingsRoute
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks.QuizDetailsBlanksScreen
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.blanks.QuizDetailsBlanksViewModel
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.home.QuizDetailsHomeEvent
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.home.QuizDetailsHomeScreen
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.home.QuizDetailsHomeViewModel
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.standings.QuizDetailsStandingsScreen
import com.bashkevich.quizchecker.adminApp.screens.quizdetails.standings.QuizDetailsStandingsViewModel
import com.bashkevich.quizchecker.components.icons.IconGroup
import com.bashkevich.quizchecker.components.icons.default_icons.ArrowBack
import com.bashkevich.quizchecker.components.icons.default_icons.Blanks
import com.bashkevich.quizchecker.components.icons.default_icons.Home
import com.bashkevich.quizchecker.components.icons.default_icons.Standings
import org.jetbrains.compose.resources.stringResource
import com.bashkevich.quizchecker.resources.*
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizDetailsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val homeViewModel = koinViewModel<QuizDetailsHomeViewModel>()
    val blanksViewModel = koinViewModel<QuizDetailsBlanksViewModel>()
    val standingsViewModel = koinViewModel<QuizDetailsStandingsViewModel>()

    val navController = LocalNavHostController.current

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.quiz_details_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = IconGroup.Default.ArrowBack,
                            contentDescription = stringResource(Res.string.back_button)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            imageVector = IconGroup.Default.Home,
                            contentDescription = stringResource(Res.string.home_tab_description)
                        )
                    },
                    label = { Text(stringResource(Res.string.home_tab_title)) }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            imageVector = IconGroup.Default.Blanks,
                            contentDescription = stringResource(Res.string.blanks_tab_description)
                        )
                    },
                    label = { Text(stringResource(Res.string.blanks_tab_title)) }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(
                            imageVector = IconGroup.Default.Standings,
                            contentDescription = stringResource(Res.string.standings_tab_description)
                        )
                    },
                    label = { Text(stringResource(Res.string.standings_tab_title)) }
                )
            }
        },
        modifier = modifier
    ) { contentPadding ->
        when {
            state.isLoading -> {
                QuizDetailsLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                )
            }
            else -> {
                when (selectedTab) {
                    0 -> QuizDetailsHomeScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding),
                        quiz = state.quiz,
                        onEvent = { homeViewModel.onEvent(it) },
                        onNavigateToSettings = { navController.navigate(SettingsRoute) }
                    )
                    1 -> QuizDetailsBlanksScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding),
                        viewModel = blanksViewModel
                    )
                    2 -> QuizDetailsStandingsScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                    )
                }
            }
        }
    }
}
