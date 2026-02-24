package com.bashkevich.quizchecker.adminApp.screens.quizlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bashkevich.quizchecker.adminApp.AddQuizRoute
import com.bashkevich.quizchecker.adminApp.LocalNavHostController
import com.bashkevich.quizchecker.adminApp.QuizDetailsRoute
import com.bashkevich.quizchecker.components.QuizWeekItem
import com.bashkevich.quizchecker.components.icons.IconGroup
import com.bashkevich.quizchecker.components.icons.default_icons.Add
import com.bashkevich.quizchecker.model.quiz.domain.Quiz

@Composable
fun QuizListScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizListViewModel
) {

    Column(
        modifier = Modifier
            .then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val navController = LocalNavHostController.current

        val state by viewModel.state.collectAsStateWithLifecycle()

        when {
            state.isLoading -> {
                QuizListLoading()
            }

            state.quizList.isEmpty() -> {
                QuizListEmpty(onAddQuizClick = { navController.navigate(AddQuizRoute) })
            }

            else -> {
                QuizListContent(
                    state = state,
                    onQuizSelected = { quiz ->
                        navController.navigate(QuizDetailsRoute(quiz.quizDay.id))
                    },
                    onAddQuizClick = { navController.navigate(AddQuizRoute) })
            }
        }
    }
}


@Composable
private fun QuizListContent(
    modifier: Modifier = Modifier,
    state: QuizListScreenState,
    onQuizSelected: (Quiz) -> Unit,
    onAddQuizClick: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddQuizClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = IconGroup.Default.Add, contentDescription = "Add quiz")
            }
        },
        modifier = Modifier.then(modifier)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 32.dp)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Event Schedule",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground,
            )


            LazyColumn(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
            ) {
                items(state.quizList) { quizWeek ->
                    QuizWeekItem(
                        quiz = quizWeek,
                        onItemSelected = onQuizSelected
                    )
                }
            }
        }
    }
}
