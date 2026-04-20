package com.bashkevich.quizchecker.model.blank_template.domain

data class BlankTemplate(
    val id: Int,
    val quizWeekId: Int,
    val roundNumber: Int,
    val title: String,
    val slotsAmount: Int,
    val slots: List<SlotTemplate>
)

data class SlotTemplate(
    val id: Int,
    val slotNumber: Int,
    val checkInstructions: String?,
    val answersAmount: Int,
    val answer: AnswerTemplate?
)

data class AnswerTemplate(
    val id: Int,
    val answer: String,
    val points: Double
)
