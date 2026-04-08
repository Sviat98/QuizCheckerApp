package com.bashkevich.quizchecker.model.quiz.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bashkevich.quizchecker.model.quiz.local.QuizEventEntity
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizDayEntity
import com.bashkevich.quizchecker.model.quiz.local.entity.QuizWeekEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizWeek(quizWeek: QuizWeekEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizDay(quizDay: QuizDayEntity)

    @Query("DELETE FROM quiz_day WHERE quiz_day_id = :quizDayId")
    suspend fun deleteQuizDayById(quizDayId: String)

    @Query("DELETE FROM quiz_week WHERE id = :quizWeekId")
    suspend fun deleteQuizWeekById(quizWeekId: String)

    @Query("DELETE FROM quiz_day")
    suspend fun deleteAllQuizDays()

    @Query("DELETE FROM quiz_week")
    suspend fun deleteAllQuizWeeks()

    @Transaction
    suspend fun insertQuizList(quizList: List<QuizEventEntity>) {
        // Delete all old data first
        deleteAllQuizDays()
        deleteAllQuizWeeks()

        // Insert new data
        quizList.forEach { quizEvent ->
            insertQuizWeek(quizEvent.quizWeek)
            insertQuizDay(quizEvent.quizDay)
        }
    }

    @Transaction
    suspend fun insertQuiz(quizEvent: QuizEventEntity) {
        insertQuizDay(quizEvent.quizDay)
        insertQuizWeek(quizEvent.quizWeek)
    }

    @Transaction
    @Query("""
        SELECT *
        FROM quiz_day
        ORDER BY quiz_day.date_time
    """)
    fun getQuizList(): Flow<List<QuizEventEntity>>

    @Transaction
    @Query("""
        SELECT *
        FROM quiz_day
        WHERE quiz_day_id = :quizEventId
    """)
    fun observeQuizEventById(quizEventId: String): Flow<QuizEventEntity?>
}
