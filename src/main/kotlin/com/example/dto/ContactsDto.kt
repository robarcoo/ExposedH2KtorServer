package com.example.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactsDto(
    @SerialName("candidate_id")
    val candidateId : Int,
    val phone: String,
    val email: String
)