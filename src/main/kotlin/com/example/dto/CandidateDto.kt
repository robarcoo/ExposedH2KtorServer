package com.example.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CandidateDto(
    val id : Int,
    @SerialName("candidate_info")
    val candidateInfo: CandidateInfoDto,
    val education: List<EducationDto>,
    @SerialName("job_experience")
    val jobExperience: List<JobExperienceDto>,
    @SerialName("free_form")
    val freeForm: String
)
