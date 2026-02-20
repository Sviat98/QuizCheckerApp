package com.bashkevich.quizchecker.model.quiz

import com.bashkevich.quizchecker.core.ktor.LoadResult
import com.bashkevich.quizchecker.core.ktor.NetworkError
import com.bashkevich.quizchecker.core.ktor.doOnSuccess
import com.bashkevich.quizchecker.core.ktor.mapError
import com.bashkevich.quizchecker.core.ktor.mapSuccess
import com.bashkevich.quizchecker.core.ktor.toNetworkError
import com.bashkevich.quizchecker.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import com.bashkevich.quizchecker.model.quiz.domain.Quiz
import com.bashkevich.quizchecker.model.quiz.local.QuizLocalDataSource
import com.bashkevich.quizchecker.model.quiz.mapper.entityToDomain
import com.bashkevich.quizchecker.model.quiz.mapper.toEntity
import com.bashkevich.quizchecker.model.quiz.mapper.toDomain
import com.bashkevich.quizchecker.model.quiz.remote.QuizDayDto
import com.bashkevich.quizchecker.model.quiz.remote.QuizEventDto
import com.bashkevich.quizchecker.model.quiz.remote.QuizRemoteDataSource

class QuizRepositoryImpl(
    private val quizRemoteDataSource: QuizRemoteDataSource,
    private val quizLocalDataSource: QuizLocalDataSource
) : QuizRepository {

    override suspend fun getUpcomingQuizList(playerId: String): LoadResult<List<Quiz>, NetworkError> =
        quizRemoteDataSource.getUpcomingQuizList(playerId)
            .mapSuccess { dtoList ->
                dtoList.map { singleDto ->
                    singleDto.toEntity()
                }
            }
            .doOnSuccess { quizEntities ->
                withContext(Dispatchers.IO) {
                    quizLocalDataSource.insertUpcomingQuizList(
                        quizEntities
                    )
                }
            }
            .mapSuccess { entries -> entries.map { quizEntity -> quizEntity.entityToDomain() } }
            .mapError { it.toNetworkError() }


    override suspend fun getQuizListResult(): LoadResult<List<Quiz>, NetworkError> =
        quizRemoteDataSource.getQuizListResult()
            .mapSuccess { dtos ->
                dtos.map { singleDto ->
                    singleDto.toEntity()
                }
            }
            .doOnSuccess { quizEntities ->
                withContext(Dispatchers.IO) {
                    quizLocalDataSource.insertQuizList(
                        quizEntities
                    )
                }
            }
            .mapSuccess { entries -> entries.map { quizEntity -> quizEntity.entityToDomain() } }
            .mapError { it.toNetworkError() }

    override suspend fun getQuizEventById(id: String): LoadResult<Quiz, NetworkError> =
        quizRemoteDataSource.getQuizEventById(id)
            .mapSuccess { dto ->
                dto.toEntity()
            }
            .doOnSuccess { quizEntity ->
                withContext(Dispatchers.IO) {
                    quizLocalDataSource.insertQuiz(
                        quizEntity
                    )
                }
            }
            .mapSuccess { entity -> entity.entityToDomain() }
            .mapError { it.toNetworkError() }

    override suspend fun addQuiz(
        title: String,
        eventDateTime: LocalDateTime,
        registrationDateTime: LocalDateTime,
        city: String
    ) {

        val quizDto = QuizEventDto(
            title = title,
            quizDay = QuizDayDto(
                id = "", seasonNumber = 7, dateTime = eventDateTime,
                registrationTimeBegin = registrationDateTime,
                city = city, registrationOpen = false, status = Status.NOT_STARTED
            ),
        )
        quizRemoteDataSource.addQuiz(quizDto)
            .mapSuccess { singleDto ->
                singleDto.toEntity()
            }
            .doOnSuccess { quizEntity ->
                withContext(Dispatchers.IO) {
                    quizLocalDataSource.insertQuiz(
                        quizEntity
                    )
                }
            }
    }

    override fun observeQuizList() =
        quizLocalDataSource.getQuizList()
            .map { quizEntities -> quizEntities.map { it.entityToDomain() } }


    override fun observeQuizSchedule(showRegisteredStatus: Boolean) =
        quizLocalDataSource.getQuizSchedule()
            .map { quizEntities -> quizEntities.map { it.toDomain(showRegisteredStatus) } }


    override fun observeCurrentQuiz(quizId: String) =
        quizLocalDataSource.observeQuizEventById(quizId).map { quiz ->
            quiz?.entityToDomain() ?: Quiz.DEFAULT
        }

    override fun observeCurrentQuizId() = quizLocalDataSource.currentQuizId

    override fun observeSelectedQuiz() = quizLocalDataSource.selectedQuiz

    override fun setCurrentQuizId(quizId: String) = quizLocalDataSource.setQuizId(quizId)

    override fun setSelectedQuiz(quizWeekId: String, quizId: String) =
        quizLocalDataSource.setSelectedQuiz(quizWeekId, quizId)

    override suspend fun startQuiz(
        quizId: String
    ) {
        quizRemoteDataSource.startQuiz(quizId)
            .mapSuccess { singleDto ->
                singleDto.toEntity()
            }
            .doOnSuccess { quizEntity ->
                withContext(Dispatchers.IO) {
                    //quizLocalDataSource.deleteQuizList()
                    quizLocalDataSource.insertQuiz(
                        quizEntity
                    )
                }
            }
    }

    override suspend fun finishQuiz(
        quizId: String
    ) {
        quizRemoteDataSource.finishQuiz(quizId)
            .mapSuccess { singleDto ->
                singleDto.toEntity()
            }
            .doOnSuccess { quizEntity ->
                withContext(Dispatchers.IO) {
                    quizLocalDataSource.insertQuiz(
                        quizEntity
                    )
                }
            }
    }

    override suspend fun revertQuiz(quizId: String) {
        quizRemoteDataSource.revertQuiz(quizId)
            .mapSuccess { singleDto ->
                singleDto.toEntity()
            }
            .doOnSuccess { quizEntity ->
                withContext(Dispatchers.IO) {
                    quizLocalDataSource.insertQuiz(
                        quizEntity
                    )
                }
            }
    }

}