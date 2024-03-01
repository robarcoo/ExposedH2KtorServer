package com.example.models

import com.example.models.CandidateInfos.uniqueIndex
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

class Contact (id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Contact>(Contacts)
    var candidateId by Contacts.candidateId.uniqueIndex()
    var phone by Contacts.phone
    var email by Contacts.email
}

object Contacts : IntIdTable() {
    val candidateId  = reference("candidate_id", CandidateInfos.id, onDelete = ReferenceOption.CASCADE)
    val phone = varchar("phone", 256)
    val email = varchar("email", 256)
}
