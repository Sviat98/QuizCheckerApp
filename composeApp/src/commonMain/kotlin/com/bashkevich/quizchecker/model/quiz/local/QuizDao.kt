package com.bashkevich.quizchecker.model.quiz.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.bashkevich.quizchecker.QuizCheckerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.bashkevich.quizchecker.model.quiz.mapper.toEntity


class QuizDao(
   database: QuizCheckerDatabase
) {

    private val quizQueries = database.quizQueries

    fun insertQuizList(quizWeekWithQuizDayList: List<QuizWeekWithQuizDay>) {
        quizQueries.transaction {
            val quizList = quizQueries.getQuizList().executeAsList()

            quizList.forEach {
                quizQueries.deleteQuizDay(it.quiz_day_id)
                quizQueries.deleteQuizWeek(it.quiz_week_id)
            }

            quizWeekWithQuizDayList.forEach { quizWeekWithQuizDay ->
                insertQuiz(quizWeekWithQuizDay)
            }
        }
    }

    fun insertQuiz(quizWeekWithQuizDay: QuizWeekWithQuizDay) {
        quizQueries.transaction {
            quizQueries.insertQuizWeek(quizWeekWithQuizDay.quizWeekEntity)
            quizQueries.insertQuizDay(quizWeekWithQuizDay.quizDay)
        }
    }

    fun getQuizList(): Flow<List<QuizWeekWithQuizDay>> {
        return quizQueries.getQuizList().asFlow().mapToList(Dispatchers.IO)
            .map { quizList -> quizList.map { it.toEntity() } }
    }

    fun getQuizSchedule(): Flow<List<QuizEventItemEntity>> {
        return quizQueries.getQuizSchedule().asFlow().mapToList(Dispatchers.IO)
            .map { quizList -> quizList.map { it.toEntity() } }
    }

    fun getQuizEventById(quizId: String)= quizQueries.getQuizEventById(quizId).executeAsOne().toEntity()

    fun observeQuizEventById(quizId: String)= quizQueries.getQuizEventById(quizId).asFlow().mapToOneOrNull(Dispatchers.IO).map { it?.toEntity() }

    fun insertUpcomingQuizList(
        upcomingQuizEntities: List<QuizWeekWithQuizDay>
    ) {
        quizQueries.transaction {
            val upcomingQuizListToDelete = quizQueries.getUpcomingQuizList().executeAsList()

            upcomingQuizListToDelete.forEach {
                quizQueries.deleteQuizDay(it.quiz_day_id)
                quizQueries.deleteQuizWeek(it.quiz_day_id)
            }

            upcomingQuizEntities.forEach { quizWeekWithQuizDay ->
                insertQuiz(quizWeekWithQuizDay)
            }
        }
    }
}