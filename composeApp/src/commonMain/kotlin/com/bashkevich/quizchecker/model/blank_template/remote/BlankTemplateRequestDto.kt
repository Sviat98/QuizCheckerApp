package com.bashkevich.quizchecker.model.blank_template.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlankTemplateRequestDto(
    @SerialName("prompt")
    val prompt: String
)