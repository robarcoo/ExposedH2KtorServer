package com.example.models


import com.example.models.Vacancy.Companion.referrersOn
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import javax.swing.text.html.parser.Entity

class Company (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Company>(Companies)
    var name by Companies.name
    var activity by Companies.activity
    val vacancies by Vacancy referrersOn Vacancies.companyId
    var contacts by Companies.contacts
}
object Companies : IntIdTable() {
    val name = varchar("name", 256)
    val activity = varchar("activity", 256)
    val contacts = varchar("contacts", 256)
}












