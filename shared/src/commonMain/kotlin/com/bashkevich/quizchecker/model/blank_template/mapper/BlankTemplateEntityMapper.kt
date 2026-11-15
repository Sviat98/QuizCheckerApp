package com.bashkevich.quizchecker.model.blank_template.mapper

import com.bashkevich.quizchecker.model.blank_template.domain.AnswerTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.SlotTemplate
import com.bashkevich.quizchecker.model.blank_template.local.BlankTemplateWithSlots
import com.bashkevich.quizchecker.model.blank_template.local.SlotTemplateWithAnswers
import com.bashkevich.quizchecker.model.blank_template.local.entity.AnswerTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.BlankTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.local.entity.SlotTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.remote.AnswerTemplateDto
import com.bashkevich.quizchecker.model.blank_template.remote.BlankTemplateDto
import com.bashkevich.quizchecker.model.blank_template.remote.SlotTemplateDto

// DTO -> Entity

data class BlankTemplateEntityData(
    val blankTemplate: BlankTemplateEntity,
    val slots: List<SlotTemplateEntity>,
    val slotAnswers: Map<Int, List<AnswerTemplateEntity>>
)

fun BlankTemplateDto.toEntityData() = BlankTemplateEntityData(
    blankTemplate = BlankTemplateEntity(
        id = id,
        quizWeekId = quizWeekId.toString(),
        roundNumber = roundNumber,
        title = title,
        slotsAmount = slotsAmount
    ),
    slots = slots.map { slotDto ->
        SlotTemplateEntity(
            id = slotDto.id,
            blankTemplateId = id,
            slotNumber = slotDto.slotNumber,
            checkInstructions = slotDto.checkInstructions,
            answersAmount = slotDto.answersAmount
        )
    },
    slotAnswers = slots.mapNotNull { slotDto ->
        slotDto.answer?.toEntity()?.let { slotDto.id to listOf(it) }
    }.toMap()
)

fun AnswerTemplateDto.toEntity() = AnswerTemplateEntity(
    id = id,
    answer = answer,
    points = points
)

// Entity -> Domain

fun BlankTemplateWithSlots.toDomain(
    answersBySlotId: Map<Int, List<AnswerTemplateEntity>>
) = BlankTemplate(
    id = blankTemplate.id,
    quizWeekId = blankTemplate.quizWeekId,
    roundNumber = blankTemplate.roundNumber,
    title = blankTemplate.title,
    slotsAmount = blankTemplate.slotsAmount,
    slots = slots.map { slot ->
        SlotTemplate(
            id = slot.id,
            slotNumber = slot.slotNumber,
            checkInstructions = slot.checkInstructions,
            answersAmount = slot.answersAmount,
            answers = (answersBySlotId[slot.id] ?: emptyList()).map { it.toDomain() }
        )
    }
)

fun SlotTemplateWithAnswers.toDomain() = SlotTemplate(
    id = slot.id,
    slotNumber = slot.slotNumber,
    checkInstructions = slot.checkInstructions,
    answersAmount = slot.answersAmount,
    answers = answers.map { it.toDomain() }
)

fun AnswerTemplateEntity.toDomain() = AnswerTemplate(
    id = id,
    answer = answer,
    points = points
)

fun BlankTemplateEntityData.toDomain() = BlankTemplate(
    id = blankTemplate.id,
    quizWeekId = blankTemplate.quizWeekId,
    roundNumber = blankTemplate.roundNumber,
    title = blankTemplate.title,
    slotsAmount = blankTemplate.slotsAmount,
    slots = slots.map { slot ->
        SlotTemplate(
            id = slot.id,
            slotNumber = slot.slotNumber,
            checkInstructions = slot.checkInstructions,
            answersAmount = slot.answersAmount,
            answers = (slotAnswers[slot.id] ?: emptyList()).map { it.toDomain() }
        )
    }
)
