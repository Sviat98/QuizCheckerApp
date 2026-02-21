package com.bashkevich.quizchecker.model.quiz.remote

import com.bashkevich.quizchecker.core.ktor.LoadResult
import com.bashkevich.quizchecker.core.ktor.runOperationCatching
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getRegisteredQuizList(playerId: String): LoadResult<List<QuizEventDto>, Throwable> = withContext(
        Dispatchers.IO){
        runOperationCatching {
            val quizDtoList = httpClient.get {
                contentType(ContentType.Application.Json)
                parameter("playerId", playerId)
                url {
                    encodedPath = "/quizEvents/registered"
                }
            }.body<List<QuizEventDto>>()
            quizDtoList
        }
    }
    suspend fun getUpcomingQuizList(playerId: String): LoadResult<List<QuizEventDto>, Throwable> = withContext(
        Dispatchers.IO){
        runOperationCatching {
            val quizDtoList = httpClient.get {
                contentType(ContentType.Application.Json)
                parameter("playerId", playerId)
                url {
                    encodedPath = "/quizEvents/upcoming"
                }
            }.body<UpcomingQuizWeeksResponse>().quizEvents
            quizDtoList
        }
    }

    suspend fun getQuizListResult(): LoadResult<List<QuizEventDto>, Throwable> = withContext(
        Dispatchers.IO){
        runOperationCatching {
            val quizDtoList = httpClient.get {
                contentType(ContentType.Application.Json)
                url {
                    encodedPath = "/quizEvents"
                }
            }.body<List<QuizEventDto>>()
            quizDtoList
        }
    }

    suspend fun getQuizEventById(id: String): LoadResult<QuizEventDto, Throwable> = withContext(
        Dispatchers.IO){
        runOperationCatching {
            val quizEventDto = httpClient.get {
                contentType(ContentType.Application.Json)
                url {
                    encodedPath = "/quizEvents/$id"
                }
            }.body<QuizEventDto>()
            quizEventDto
        }
    }

    suspend fun addQuiz(quizEventDto: QuizEventDto): LoadResult<QuizEventDto, Throwable> = withContext(
        Dispatchers.IO) {
        runOperationCatching {
            val quizDto = httpClient.post {
                contentType(ContentType.Application.Json)
                setBody(quizEventDto)
                url {
                    encodedPath = "/quizEvents"
                }
            }.body<QuizEventDto>()
            quizDto
        }
    }

    suspend fun startQuiz(quizId: String): LoadResult<QuizEventDto, Throwable> = withContext(
        Dispatchers.IO) {
        runOperationCatching {
            val quizDto = httpClient.post {
                contentType(ContentType.Application.Json)
                url {
                    encodedPath = "quizEvents/$quizId/start"
                }
            }.body<QuizEventDto>()
            quizDto
        }
    }

    suspend fun finishQuiz(quizId: String): LoadResult<QuizEventDto, Throwable> = withContext(
        Dispatchers.IO) {
        runOperationCatching {
            val quizDto = httpClient.post {
                contentType(ContentType.Application.Json)
                url {
                    encodedPath = "quizEvents/$quizId/finish"
                }
            }.body<QuizEventDto>()
            quizDto
        }
    }


    suspend fun revertQuiz(quizId: String): LoadResult<QuizEventDto, Throwable> = withContext(
        Dispatchers.IO) {
        runOperationCatching {
            val quizDto = httpClient.post {
                contentType(ContentType.Application.Json)
                url {
                    encodedPath = "quizEvents/$quizId/revert"
                }
            }.body<QuizEventDto>()
            quizDto
        }
    }
}