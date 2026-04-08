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

    // Transaction for inserting quiz list
//    @Transaction
//    suspend fun insertQuizList(quizWeekWithQuizDayList: List<QuizWeekWithQuizDay>) {
//        // Delete existing data
//        quizWeekWithQuizDayList.forEach { quiz ->
//            deleteQuizDayById(quiz.quizDay.quiz_day_id)
//            deleteQuizWeekById(quiz.quizWeek.id)
//        }
//
//        // Insert new data
//        quizWeekWithQuizDayList.forEach { quiz ->
//            insertQuizWeek(quiz.quizWeek)
//            insertQuizDay(quiz.quizDay)
//        }
//    }
//
//    @Transaction
//    suspend fun insertQuiz(quizWeekWithQuizDay: QuizWeekWithQuizDay) {
//        insertQuizWeek(quizWeekWithQuizDay.quizWeek)
//        insertQuizDay(quizWeekWithQuizDay.quizDay)
//    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizWeek(quizWeek: QuizWeekEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizDay(quizDay: QuizDayEntity)

    @Query("DELETE FROM quiz_day WHERE quiz_day_id = :quizDayId")
    suspend fun deleteQuizDayById(quizDayId: String)

    @Query("DELETE FROM quiz_week WHERE id = :quizWeekId")
    suspend fun deleteQuizWeekById(quizWeekId: String)

    // Complex JOIN queries
    @Transaction
    @Query("""
        SELECT *
        FROM quiz_day
        ORDER BY quiz_day.date_time
    """)
    fun getQuizList(): Flow<List<QuizEventEntity>>

//    @Transaction
//    @Query("""
//        SELECT quiz_day.*, quiz_week.*,
//               (SELECT COUNT(*) FROM registered_event re
//                WHERE re.quiz_day_id = quiz_day.quiz_day_id) as reg_flag
//        FROM quiz_day
//        INNER JOIN quiz_week ON quiz_week.id = quiz_day.quiz_week_id
//        WHERE NOT EXISTS (
//            SELECT 1 FROM registered_event re
//            WHERE re.quiz_day_id != quiz_day.quiz_day_id
//            AND re.quiz_week_id = quiz_day.quiz_week_id
//        )
//        AND quiz_day.status IN ('NOT_STARTED', 'IN_PROGRESS')
//        ORDER BY quiz_day.date_time
//    """)
//    fun getQuizSchedule(): Flow<List<QuizEventItemEntity>>

//    @Transaction
//    @Query("""
//        SELECT quiz_day.*, quiz_week.*
//        FROM quiz_day
//        INNER JOIN quiz_week ON quiz_week.id = quiz_day.quiz_week_id
//        WHERE quiz_day.quiz_day_id = :quizId
//    """)
//    fun observeQuizEventById(quizId: String): Flow<QuizWeekWithQuizDay?>

//    @Transaction
//    @Query("""
//        SELECT quiz_day.*, quiz_week.*
//        FROM quiz_day
//        INNER JOIN quiz_week ON quiz_week.id = quiz_day.quiz_week_id
//        WHERE quiz_day.quiz_day_id = :quizId
//        LIMIT 1
//    """)
//    suspend fun getQuizEventById(quizId: String): QuizWeekWithQuizDay?

//    @Transaction
//    @Query("""
//        SELECT quiz_day.*, quiz_week.*
//        FROM quiz_day
//        INNER JOIN quiz_week ON quiz_week.id = quiz_day.quiz_week_id
//        WHERE quiz_day.status = 'NOT_STARTED'
//        ORDER BY quiz_day.date_time
//    """)
//    fun getUpcomingQuizList(): Flow<List<QuizWeekWithQuizDay>>

//    @Transaction
//    suspend fun insertUpcomingQuizList(upcomingQuizEntities: List<QuizWeekWithQuizDay>) {
//        // Delete existing upcoming quizzes
//        val existingList = getUpcomingQuizListSync()
//        existingList.forEach { quiz ->
//            deleteQuizDayById(quiz.quizDay.quiz_day_id)
//            deleteQuizWeekById(quiz.quizWeekEntity.id)
//        }
//
//        // Insert new data
//        upcomingQuizEntities.forEach { quiz ->
//            insertQuiz(quiz)
//        }
//    }

//    @Query("""
//        SELECT quiz_day.*, quiz_week.*
//        FROM quiz_day
//        INNER JOIN quiz_week ON quiz_week.id = quiz_day.quiz_week_id
//        WHERE quiz_day.status = 'NOT_STARTED'
//        ORDER BY quiz_day.date_time
//    """)
//    suspend fun getUpcomingQuizListSync(): List<QuizWeekWithQuizDay>
}
