package com.bashkevich.quizchecker.model.blank_template.remote

import com.bashkevich.quizchecker.core.ktor.LoadResult
import com.bashkevich.quizchecker.core.ktor.runOperationCatching
import com.bashkevich.quizchecker.model.blank_template.BlankTemplateDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BlankTemplateRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getBlankTemplates(quizId: String): LoadResult<List<BlankTemplateDto>, Throwable> =
        withContext(Dispatchers.IO) {
            runOperationCatching {
                httpClient.get {
                    contentType(ContentType.Application.Json)
                    url {
                        encodedPath = "/quiz-weeks/$quizId/blank-templates"
                    }
                }.body<List<BlankTemplateDto>>()
            }
        }
}
