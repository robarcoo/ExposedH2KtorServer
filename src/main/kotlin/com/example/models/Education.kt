package com.example.models

import com.example.models.CandidateInfos.uniqueIndex
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

class Education (id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Education>(Educations)
    var candidateId by Educations.candidateId.uniqueIndex()
    var type by Educations.type
    var yearStart by Educations.yearStart
    var yearEnd by Educations.yearEnd
    var description by Educations.description
}

object Educations: IntIdTable() {
    val candidateId = reference("candidate_id", Candidates.id, onDelete = ReferenceOption.CASCADE)
    val type = varchar("type", 256)
    val yearStart = varchar("year_start", 256)
    val yearEnd = varchar("year_end", 256)
    val description = varchar("description", 256)
}
