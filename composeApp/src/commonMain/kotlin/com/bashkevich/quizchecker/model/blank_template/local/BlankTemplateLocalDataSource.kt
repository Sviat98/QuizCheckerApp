package com.bashkevich.quizchecker.model.blank_template.local

import com.bashkevich.quizchecker.model.blank_template.local.dao.BlankTemplateDao
import com.bashkevich.quizchecker.model.blank_template.local.entity.AnswerTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.BlankTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.SlotTemplateEntity
import kotlinx.coroutines.flow.Flow

class BlankTemplateLocalDataSource(
    private val blankTemplateDao: BlankTemplateDao
) {

    suspend fun insertBlankTemplateWithSlotsAndAnswers(
        blankTemplate: BlankTemplateEntity,
        slots: List<SlotTemplateEntity>,
        slotAnswers: Map<Int, List<AnswerTemplateEntity>>
    ) {
        blankTemplateDao.insertBlankTemplateWithSlotsAndAnswers(
            blankTemplate, slots, slotAnswers
        )
    }

    fun observeBlankTemplates(quizWeekId: String): Flow<List<BlankTemplateWithSlots>> {
        return blankTemplateDao.observeBlankTemplatesByQuizWeekId(quizWeekId)
    }

    fun observeSlotsWithAnswers(quizWeekId: String): Flow<List<SlotTemplateWithAnswers>> {
        return blankTemplateDao.observeSlotsWithAnswersByQuizWeekId(quizWeekId)
    }

    fun observeSlotTemplateWithAnswers(slotId: Int): Flow<SlotTemplateWithAnswers?> {
        return blankTemplateDao.observeSlotTemplateWithAnswers(slotId)
    }

    suspend fun deleteBlankTemplatesByQuizWeekId(quizWeekId: String) {
        blankTemplateDao.deleteBlankTemplatesByQuizWeekId(quizWeekId)
    }
}
