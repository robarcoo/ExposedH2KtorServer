package com.example.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EducationDto(
    @SerialName("candidate_id")
    val candidateId: Int,
    var type: String,
    @SerialName("year_start")
    var yearStart: String,
    @SerialName("year_end")
    var yearEnd: String,
    var description: String
)