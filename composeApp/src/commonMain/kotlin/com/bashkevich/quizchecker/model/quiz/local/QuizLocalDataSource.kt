package com.bashkevich.quizchecker.model.quiz.local

import com.bashkevich.quizchecker.model.quiz.local.dao.QuizDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.bashkevich.quizchecker.model.quiz.domain.Quiz


class QuizLocalDataSource(
    private val quizDao: QuizDao
) {

    private val _selectedQuiz = MutableStateFlow(Pair(Quiz.DEFAULT.id,Quiz.DEFAULT.quizDay.id))


    private val _currentQuizId = MutableStateFlow(Quiz.DEFAULT.quizDay.id)


    val currentQuizId: StateFlow<String>
        get() = _currentQuizId

    val selectedQuiz: StateFlow<Pair<String,String>>
        get() = _selectedQuiz.asStateFlow()

    fun setQuizId(quizId: String) {
        _currentQuizId.value = quizId
    }

    fun setSelectedQuiz(quizWeekId: String, quizId: String) {
        _selectedQuiz.value = Pair(quizWeekId,quizId)
    }

    // Direct delegation to Room DAO
    suspend fun insertQuizList(quizEvents: List<QuizEventEntity>) {
        quizDao.insertQuizList(quizEvents)
    }

    suspend fun insertQuiz(quizEventEntity: QuizEventEntity) {
        quizDao.insertQuiz(quizEventEntity)
    }

    fun getQuizList(): Flow<List<QuizEventEntity>> {
        return quizDao.getQuizList()
    }

//    fun getQuizSchedule(): Flow<List<QuizEventEntity>> {
//        return quizDao.getQuizSchedule()
//    }
//
//    suspend fun getQuizEventById(quizId: String): QuizWeekWithQuizDay? {
//        return quizDao.getQuizEventById(quizId)
//    }
//
    fun observeQuizEventById(quizId: String): Flow<QuizEventEntity?> {
        return quizDao.observeQuizEventById(quizId)
    }
//
//    suspend fun insertUpcomingQuizList(upcomingQuizEntities: List<QuizWeekWithQuizDay>) {
//        quizDao.insertUpcomingQuizList(upcomingQuizEntities)
//    }
}