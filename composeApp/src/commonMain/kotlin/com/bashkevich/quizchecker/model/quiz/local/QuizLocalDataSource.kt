package com.bashkevich.quizchecker.model.quiz.local

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

    fun insertQuizList(quizEntities: List<QuizWeekWithQuizDay>) =
        quizDao.insertQuizList(quizEntities)

    fun insertUpcomingQuizList(upcomingQuizEntities: List<QuizWeekWithQuizDay>) =
        quizDao.insertUpcomingQuizList(upcomingQuizEntities)

    fun insertQuiz(quizEntity: QuizWeekWithQuizDay) = quizDao.insertQuiz(quizEntity)

    fun getQuizList() = quizDao.getQuizList()

    fun getQuizSchedule(): Flow<List<QuizEventItemEntity>> = quizDao.getQuizSchedule()

    fun observeQuizEventById(quizId: String): Flow<QuizWeekWithQuizDay?> = quizDao.observeQuizEventById(quizId)

}