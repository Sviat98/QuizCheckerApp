package com.bashkevich.quizchecker.model.blank_template.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "slot_template_answer_template_cross_ref",
    primaryKeys = ["slot_template_id", "answer_template_id"],
    foreignKeys = [
        ForeignKey(
            entity = SlotTemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["slot_template_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AnswerTemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["answer_template_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("slot_template_id"),
        Index("answer_template_id")
    ]
)
data class SlotTemplateAnswerTemplateCrossRef(
    @ColumnInfo("slot_template_id")
    val slotTemplateId: Int,
    @ColumnInfo("answer_template_id")
    val answerTemplateId: Int
)
