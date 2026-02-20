package com.bashkevich.quizchecker.model.quiz

import com.bashkevich.quizchecker.core.ktor.LoadResult
import com.bashkevich.quizchecker.core.ktor.NetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDateTime
import com.bashkevich.quizchecker.model.quiz.domain.Quiz

interface QuizRepository {

    suspend fun getQuizListResult(): LoadResult<List<Quiz>, NetworkError>

    suspend fun getQuizEventById(id: String): LoadResult<Quiz, NetworkError>

    suspend fun addQuiz(title: String, eventDateTime: LocalDateTime, registrationDateTime: LocalDateTime, city: String)
    fun observeQuizList(): Flow<List<Quiz>>
    fun observeQuizSchedule(showRegisteredStatus: Boolean = false): Flow<List<Quiz>>
    fun setCurrentQuizId(quizId: String)

    fun setSelectedQuiz(quizWeekId: String, quizId: String)
    fun observeCurrentQuizId(): Flow<String>
    fun observeSelectedQuiz(): StateFlow<Pair<String, String>>
    fun observeCurrentQuiz(quizId: String): Flow<Quiz>
    suspend fun startQuiz(quizId: String)

    suspend fun finishQuiz(quizId: String)

    suspend fun revertQuiz(quizId: String)
    suspend fun getUpcomingQuizList(playerId: String): LoadResult<List<Quiz>, NetworkError>
}