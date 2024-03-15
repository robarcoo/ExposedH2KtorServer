package com.example.repository

import com.example.dto.FullVacancyDto
import com.example.dto.VacancyDto
import com.example.dto.VacancyDtoWithCompanyName
import com.example.models.Companies
import com.example.models.Company
import com.example.models.Vacancies
import com.example.models.Vacancy
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class VacancyRepository {

    fun findAllVacancies() : List<VacancyDtoWithCompanyName> = transaction {
        (Vacancies innerJoin Companies)
            .selectAll()
            .map {
                VacancyDtoWithCompanyName(
                    it[Vacancies.id].value,
                    it[Vacancies.name],
                    it[Vacancies.level],
                    it[Vacancies.salary],
                    it[Companies.id].value,
                    it[Companies.name],
                    it[Vacancies.description]
                )
            }
    }

//    fun findVacancy(id: Int) : VacancyDto = transaction{
//        (Vacancies innerJoin Companies)
//            .select { Vacancies.id eq id }
//            .map {
//                VacancyDto(
//                    it[Vacancies.id].value,
//                    it[Vacancies.name],
//                    it[Vacancies.level],
//                    it[Vacancies.salary],
//                    it[Companies.name],
//                    it[Vacancies.description]
//                )
//            }[0]
//    }

    fun findFullVacancy(id: Int) : FullVacancyDto = transaction {
        Vacancies.join(Companies, JoinType.INNER, onColumn = Vacancies.companyId, otherColumn = Companies.id,
            additionalConstraint = { Vacancies.id eq id })
            .selectAll()
            .map {
                FullVacancyDto(
                    it[Vacancies.id].value,
                    it[Vacancies.name],
                    it[Vacancies.level],
                    it[Vacancies.salary],
                    it[Companies.id].value,
                    it[Companies.name],
                    it[Companies.activity],
                    it[Vacancies.description],
                    it[Companies.contacts]
                )

            }[0]
    }


    fun createVacancy(vacancy: VacancyDto): Int = transaction {
        Vacancy.new {
            name = vacancy.name
            level = vacancy.level
            salary = vacancy.salary
            companyId = EntityID(vacancy.companyId, Companies)
            description = vacancy.description
        }.id.value
    }

    fun deleteVacancy(id: Int) : Unit = transaction {
        Vacancy.findById(id)?.delete()
    }

    fun updateVacancy(id: Int, newVacancy : VacancyDto) : Unit = transaction {
        Vacancy.findById(id)?.let {
            it.name = newVacancy.name
            it.level = newVacancy.level
            it.salary = newVacancy.salary
            it.companyId = EntityID(newVacancy.companyId, Companies)
            it.description = newVacancy.description
        }
    }
}