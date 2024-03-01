package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompanyDto(
    val id: Int,
    val name: String,
    val activity: String,
    val vacancies: List<VacancyDto>,
    val contacts: String = "88005553535"
)