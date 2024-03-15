package com.example.models

import com.example.models.CandidateInfos.uniqueIndex
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption


class Vacancy (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Vacancy>(Vacancies)
    var name by Vacancies.name
    var level by Vacancies.level
    var salary by Vacancies.salary
    var companyId by Vacancies.companyId.uniqueIndex()
    var description  by Vacancies.description
}

object Vacancies : IntIdTable()  {
    val name = varchar("name", 256)
    val level = varchar("level", 256)
    val salary = integer("salary")
    var companyId = reference("company_id", Companies.id, onDelete = ReferenceOption.CASCADE)
    val description = varchar("description", 256)

}

