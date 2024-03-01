package com.example.models

import com.example.models.CandidateInfos.uniqueIndex
import kotlinx.serialization.SerialName
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption


class CandidateInfo (id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CandidateInfo>(CandidateInfos)
    var candidateId by CandidateInfos.candidateId.uniqueIndex()
    var name by CandidateInfos.name
    var profession by CandidateInfos.profession
    var sex by CandidateInfos.sex
    var birthDate by CandidateInfos.birthDate
    val contacts by Contact referrersOn Contacts.candidateId
    var relocation by CandidateInfos.relocation
}

object CandidateInfos: IntIdTable() {
    val candidateId = reference("candidate_id", Candidates.id, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 256)
    val profession = varchar("profession", 256)
    val sex = varchar("sex", 256)
    val birthDate = varchar("birth_date", 256)
    val relocation = varchar("relocation", 256)
}
