package com.bashkevich.quizchecker.model.blank_template

import com.bashkevich.quizchecker.core.ktor.LoadResult
import com.bashkevich.quizchecker.core.ktor.NetworkError
import com.bashkevich.quizchecker.model.blank_template.domain.AnswerTemplate
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate
import kotlinx.coroutines.flow.Flow

interface BlankTemplateRepository {

    suspend fun getBlankTemplates(quizId: String): LoadResult<List<BlankTemplate>, NetworkError>

    suspend fun addBlankTemplate(quizId: String, prompt: String): LoadResult<BlankTemplate, NetworkError>

    suspend fun getSlotAnswers(slotId: Int): LoadResult<List<AnswerTemplate>, NetworkError>

    fun observeBlankTemplates(quizId: String): Flow<List<BlankTemplate>>

    fun observeSlotAnswers(slotId: Int): Flow<List<AnswerTemplate>>
}
