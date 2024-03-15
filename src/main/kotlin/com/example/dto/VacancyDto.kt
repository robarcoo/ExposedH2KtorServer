package com.example.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacancyDto(
    val id: Int,
    val name: String,
    val level: String,
    val salary: Int,
    @SerialName("company_id")
    val companyId: Int,
    val description: String = "Super job, earn 300K/sec"
)

@Serializable
data class VacancyDtoWithCompanyName(
    val id: Int,
    val name: String,
    val level: String,
    val salary: Int,
    @SerialName("company_id")
    val companyId: Int,
    val company: String,
    val description: String
)

@Serializable
data class FullVacancyDto(
    val id: Int,
    val name: String,
    val level: String,
    val salary: Int,
    @SerialName("company_id")
    val companyId: Int,
    val company: String,
    val field: String,
    val description: String,
    val phone: String
)