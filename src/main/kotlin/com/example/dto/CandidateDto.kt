package com.example.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CandidateDto(
    val id : Int,
    @SerialName("candidate_info")
    val candidateInfo: CandidateInfoDto,
    val education: MutableList<EducationDto>,
    @SerialName("job_experience")
    val jobExperience: MutableList<JobExperienceDto>,
    @SerialName("free_form")
    val freeForm: String
)
