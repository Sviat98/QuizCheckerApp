package com.bashkevich.quizchecker.model.blank_template

import com.bashkevich.quizchecker.core.ktor.LoadResult
import com.bashkevich.quizchecker.core.ktor.NetworkError
import com.bashkevich.quizchecker.core.ktor.doOnSuccess
import com.bashkevich.quizchecker.core.ktor.mapError
import com.bashkevich.quizchecker.core.ktor.mapSuccess
import com.bashkevich.quizchecker.core.ktor.toNetworkError
import com.bashkevich.quizchecker.model.blank_template.domain.AnswerTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate
import com.bashkevich.quizchecker.model.blank_template.local.BlankTemplateLocalDataSource
import com.bashkevich.quizchecker.model.blank_template.local.entity.AnswerTemplateEntity
import com.bashkevich.quizchecker.model.blank_template.mapper.toDomain
import com.bashkevich.quizchecker.model.blank_template.mapper.toEntityData
import com.bashkevich.quizchecker.model.blank_template.remote.BlankTemplateRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class BlankTemplateRepositoryImpl(
    private val blankTemplateRemoteDataSource: BlankTemplateRemoteDataSource,
    private val blankTemplateLocalDataSource: BlankTemplateLocalDataSource
) : BlankTemplateRepository {

    override suspend fun getBlankTemplates(quizId: String): LoadResult<List<BlankTemplate>, NetworkError> =
        blankTemplateRemoteDataSource.getBlankTemplates(quizId)
            .mapSuccess { dtoList -> dtoList.map { it.toEntityData() } }
            .doOnSuccess { entityDataList ->
                withContext(Dispatchers.IO) {
                    entityDataList.forEach { entityData ->
                        blankTemplateLocalDataSource.insertBlankTemplateWithSlotsAndAnswers(
                            entityData.blankTemplate,
                            entityData.slots,
                            entityData.slotAnswers
                        )
                    }
                }
            }
            .mapSuccess { entityDataList -> entityDataList.map { it.toDomain() } }
            .mapError { it.toNetworkError() }

    override suspend fun addBlankTemplate(quizId: String, prompt: String): LoadResult<BlankTemplate, NetworkError> =
        blankTemplateRemoteDataSource.addBlankTemplate(quizId, prompt)
            .mapSuccess { it.toEntityData() }
            .doOnSuccess { entityData ->
                withContext(Dispatchers.IO) {
                    blankTemplateLocalDataSource.insertBlankTemplateWithSlotsAndAnswers(
                        entityData.blankTemplate,
                        entityData.slots,
                        entityData.slotAnswers
                    )
                }
            }
            .mapSuccess { it.toDomain() }
            .mapError { it.toNetworkError() }

    override suspend fun getSlotAnswers(slotId: Int): LoadResult<List<AnswerTemplate>, NetworkError> =
        blankTemplateRemoteDataSource.getSlotAnswers(slotId)
            .mapSuccess { dtoList -> dtoList.map { it.toDomain() } }
            .mapError { it.toNetworkError() }

    override fun observeBlankTemplates(quizId: String): Flow<List<BlankTemplate>> =
        combine(
            blankTemplateLocalDataSource.observeBlankTemplates(quizId),
            blankTemplateLocalDataSource.observeSlotsWithAnswers(quizId)
        ) { templates, slotsWithAnswers ->
            val answersBySlotId: Map<Int, List<AnswerTemplateEntity>> =
                slotsWithAnswers.associate { it.slot.id to it.answers }
            templates.map { it.toDomain(answersBySlotId) }
        }

    override fun observeSlotAnswers(slotId: Int): Flow<List<AnswerTemplate>> =
        blankTemplateLocalDataSource.observeSlotTemplateWithAnswers(slotId)
            .map { slotWithAnswers ->
                slotWithAnswers?.toDomain()?.answers ?: emptyList()
            }
}
