package com.bashkevich.quizchecker.model.blank_template.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "slot_template",
    foreignKeys = [
        ForeignKey(
            entity = BlankTemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["blank_template_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("blank_template_id")]
)
data class SlotTemplateEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("blank_template_id")
    val blankTemplateId: Int,
    @ColumnInfo("slot_number")
    val slotNumber: Int,
    @ColumnInfo("check_instructions")
    val checkInstructions: String?,
    @ColumnInfo("answers_amount")
    val answersAmount: Int
)
