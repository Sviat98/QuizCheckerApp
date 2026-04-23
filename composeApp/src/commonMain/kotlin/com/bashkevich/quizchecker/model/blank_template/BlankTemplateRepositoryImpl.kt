package com.bashkevich.quizchecker.model.blank_template

import com.bashkevich.quizchecker.core.ktor.LoadResult
import com.bashkevich.quizchecker.core.ktor.NetworkError
import com.bashkevich.quizchecker.core.ktor.mapError
import com.bashkevich.quizchecker.core.ktor.mapSuccess
import com.bashkevich.quizchecker.core.ktor.toNetworkError
import com.bashkevich.quizchecker.model.blank_template.domain.AnswerTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate
import com.bashkevich.quizchecker.model.blank_template.mapper.toDomain
import com.bashkevich.quizchecker.model.blank_template.remote.BlankTemplateRemoteDataSource

class BlankTemplateRepositoryImpl(
    private val blankTemplateRemoteDataSource: BlankTemplateRemoteDataSource
) : BlankTemplateRepository {

    override suspend fun getBlankTemplates(quizId: String): LoadResult<List<BlankTemplate>, NetworkError> =
        blankTemplateRemoteDataSource.getBlankTemplates(quizId)
            .mapSuccess { dtoList -> dtoList.map { it.toDomain() } }
            .mapError { it.toNetworkError() }

    override suspend fun addBlankTemplate(quizId: String, prompt: String): LoadResult<BlankTemplate, NetworkError> =
        blankTemplateRemoteDataSource.addBlankTemplate(quizId, prompt)
            .mapSuccess { it.toDomain() }
            .mapError { it.toNetworkError() }

    override suspend fun getSlotAnswers(slotId: Int): LoadResult<List<AnswerTemplate>, NetworkError> =
        blankTemplateRemoteDataSource.getSlotAnswers(slotId)
            .mapSuccess { dtoList -> dtoList.map { it.toDomain() } }
            .mapError { it.toNetworkError() }
}
