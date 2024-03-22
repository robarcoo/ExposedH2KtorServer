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
    var email by Contacts.email
    var phone by Contacts.phone
}

object Contacts : IntIdTable() {
    val candidateId  = reference("candidate_id", CandidateInfos.id, onDelete = ReferenceOption.CASCADE)
    val email = varchar("email", 256)
    val phone = varchar("phone", 256)
}
