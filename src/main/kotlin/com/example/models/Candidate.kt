package com.example.models

import com.example.models.Company.Companion.referrersOn
import com.google.gson.Gson
import kotlinx.serialization.SerialName
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class Candidate (id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Candidate>(Candidates)
    val candidateInfo by CandidateInfo referrersOn CandidateInfos.candidateId
    val education by Education referrersOn Educations.candidateId
    val jobExperience by Experience referrersOn Experiences.candidateId
    var freeForm by Candidates.freeForm
}

object Candidates : IntIdTable("Candidates") {
    val freeForm = varchar("free_form", 256)
}


val lines = object {}.javaClass.getResourceAsStream("/resume.json")?.bufferedReader()?.readText()
val gson = Gson()
val candidate: Candidate = gson.fromJson(lines, Candidate::class.java)
val candidateStorage = listOf(candidate)
