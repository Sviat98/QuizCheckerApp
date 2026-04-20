package com.bashkevich.quizchecker.model.blank_template

import com.bashkevich.quizchecker.core.ktor.LoadResult
import com.bashkevich.quizchecker.core.ktor.NetworkError
import com.bashkevich.quizchecker.model.blank_template.domain.BlankTemplate

interface BlankTemplateRepository {

    suspend fun getBlankTemplates(quizId: String): LoadResult<List<BlankTemplate>, NetworkError>
}
