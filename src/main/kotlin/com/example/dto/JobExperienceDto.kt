package com.example.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobExperienceDto(
    @SerialName("candidate_id")
    val candidateId: Int,
    @SerialName("date_start")
    var dateStart: String,
    @SerialName("date_end")
    var dateEnd: String,
    @SerialName("company_name")
    var companyName: String,
    var description: String
)