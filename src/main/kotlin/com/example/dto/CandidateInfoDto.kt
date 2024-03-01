package com.example.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CandidateInfoDto(
    @SerialName("candidate_id")
    val candidateId: Int,
    val name: String,
    val profession: String,
    val sex: String,
    @SerialName("birth_date")
    val birthDate: String,
    val contacts: ContactsDto,
    val relocation: String
)