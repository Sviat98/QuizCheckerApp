package com.bashkevich.quizchecker.model.blank_template.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bashkevich.quizchecker.model.blank_template.local.BlankTemplateWithSlots
import com.bashkevich.quizchecker.model.blank_template.local.SlotTemplateWithAnswers
import com.bashkevich.quizchecker.model.blank_template.local.entity.AnswerTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.BlankTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.SlotTemplateAnswerTemplateCrossRef
import com.bashkevich.quizchecker.model.blank_template.local.entity.SlotTemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BlankTemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlankTemplate(blankTemplate: BlankTemplateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSlotTemplate(slot: SlotTemplateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswerTemplate(answer: AnswerTemplateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSlotTemplateAnswerTemplateCrossRef(crossRef: SlotTemplateAnswerTemplateCrossRef)

    @Query("DELETE FROM blank_template WHERE quiz_week_id = :quizWeekId")
    suspend fun deleteBlankTemplatesByQuizWeekId(quizWeekId: String)

    @Query("DELETE FROM slot_template WHERE blank_template_id = :blankTemplateId")
    suspend fun deleteSlotTemplatesByBlankTemplateId(blankTemplateId: Int)

    @Query("DELETE FROM slot_template_answer_template_cross_ref WHERE slot_template_id = :slotId")
    suspend fun deleteCrossRefsBySlotTemplateId(slotId: Int)

    @Transaction
    suspend fun insertBlankTemplateWithSlotsAndAnswers(
        blankTemplate: BlankTemplateEntity,
        slots: List<SlotTemplateEntity>,
        slotAnswers: Map<Int, List<AnswerTemplateEntity>>
    ) {
        insertBlankTemplate(blankTemplate)
        slots.forEach { slot ->
            insertSlotTemplate(slot)
            slotAnswers[slot.id]?.forEach { answer ->
                insertAnswerTemplate(answer)
            }
        }
        // Cross-refs AFTER all entity inserts to avoid REPLACE cascade deletions
        slots.forEach { slot ->
            slotAnswers[slot.id]?.forEach { answer ->
                insertSlotTemplateAnswerTemplateCrossRef(
                    SlotTemplateAnswerTemplateCrossRef(
                        slotTemplateId = slot.id,
                        answerTemplateId = answer.id
                    )
                )
            }
        }
    }

    @Transaction
    @Query("""
        SELECT * FROM blank_template
        WHERE quiz_week_id = :quizWeekId
        ORDER BY round_number
    """)
    fun observeBlankTemplatesByQuizWeekId(quizWeekId: String): Flow<List<BlankTemplateWithSlots>>

    @Transaction
    @Query("""
        SELECT * FROM slot_template
        WHERE blank_template_id IN (SELECT id FROM blank_template WHERE quiz_week_id = :quizWeekId)
    """)
    fun observeSlotsWithAnswersByQuizWeekId(quizWeekId: String): Flow<List<SlotTemplateWithAnswers>>

    @Transaction
    @Query("""
        SELECT * FROM slot_template
        WHERE id = :slotId
    """)
    fun observeSlotTemplateWithAnswers(slotId: Int): Flow<SlotTemplateWithAnswers?>
}
