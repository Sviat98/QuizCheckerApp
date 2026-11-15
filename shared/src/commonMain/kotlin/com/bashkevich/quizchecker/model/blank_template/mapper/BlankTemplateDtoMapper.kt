package com.bashkevich.quizchecker.model.blank_template.mapper

import com.bashkevich.quizchecker.model.blank_template.domain.AnswerTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.SlotTemplate
import com.bashkevich.quizchecker.model.blank_template.remote.AnswerTemplateDto
import com.bashkevich.quizchecker.model.blank_template.remote.BlankTemplateDto
import com.bashkevich.quizchecker.model.blank_template.remote.SlotTemplateDto

fun BlankTemplateDto.toDomain() = BlankTemplate(
    id = id,
    quizWeekId = quizWeekId.toString(),
    roundNumber = roundNumber,
    title = title,
    slotsAmount = slotsAmount,
    slots = slots.map { it.toDomain() }
)

fun SlotTemplateDto.toDomain() = SlotTemplate(
    id = id,
    slotNumber = slotNumber,
    checkInstructions = checkInstructions,
    answersAmount = answersAmount,
    answers = listOfNotNull(answer?.toDomain())
)

fun AnswerTemplateDto.toDomain() = AnswerTemplate(
    id = id,
    answer = answer,
    points = points
)
