package com.example.models

import com.example.models.CandidateInfos.uniqueIndex
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

class Experience (id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Experience>(Experiences)
    var candidateId by Experiences.candidateId.uniqueIndex()
    var dateStart by Experiences.dateStart
    var dateEnd by Experiences.dateEnd
    var companyName by Experiences.companyName
    var description by Experiences.description
}

object Experiences: IntIdTable() {
    val candidateId = reference("candidate_id", Candidates.id, onDelete = ReferenceOption.CASCADE)
    val dateStart = varchar("date_start", 256)
    val dateEnd = varchar("date_end", 256)
    val companyName = varchar("company_name", 256)
    val description = varchar("description", 256)
}
