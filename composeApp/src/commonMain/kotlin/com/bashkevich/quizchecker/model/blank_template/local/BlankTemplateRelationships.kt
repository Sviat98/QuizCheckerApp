package com.bashkevich.quizchecker.model.blank_template.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bashkevich.quizchecker.model.blank_template.local.entity.AnswerTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.BlankTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.SlotTemplateAnswerTemplateCrossRef
import com.bashkevich.quizchecker.model.blank_template.local.entity.SlotTemplateEntity

data class SlotTemplateWithAnswers(
    @Embedded
    val slot: SlotTemplateEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = SlotTemplateAnswerTemplateCrossRef::class,
            parentColumn = "slot_template_id",
            entityColumn = "answer_template_id"
        )
    )
    val answers: List<AnswerTemplateEntity>
)

data class BlankTemplateWithSlots(
    @Embedded
    val blankTemplate: BlankTemplateEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "blank_template_id"
    )
    val slots: List<SlotTemplateEntity>
)
