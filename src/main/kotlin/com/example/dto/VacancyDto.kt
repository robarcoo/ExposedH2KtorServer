package com.example.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacancyDto(
    val id: Int,
    val profession: String,
    val level: String,
    val salary: Int,
    @SerialName("company_id")
    var companyId: Int,
    val description: String = "Super job, earn 300K/sec"
)