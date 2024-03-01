package com.example.repository

import com.example.dto.VacancyDto
import com.example.models.Companies
import com.example.models.Vacancy
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class VacancyRepository {

    fun findAllVacancies() : List<VacancyDto> = transaction {
        Vacancy
            .all()
            .map {
                VacancyDto(
                    it.id.value,
                    it.name,
                    it.level,
                    it.salary,
                    it.companyId.value,
                    it.description
                )
            }
    }

    fun findVacancy(id: Int) : VacancyDto? = transaction{
        Vacancy.findById(id)
            ?.let {
                VacancyDto(
                    it.id.value,
                    it.name,
                    it.level,
                    it.salary,
                    it.companyId.value,
                    it.description
                )
            }
    }

    fun createVacancy(vacancy: VacancyDto): Int = transaction {
        Vacancy.new {
            name = vacancy.profession
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
            it.name = newVacancy.profession
            it.level = newVacancy.level
            it.salary = newVacancy.salary
            it.companyId = EntityID(newVacancy.companyId, Companies)
            it.description = newVacancy.description
        }
    }
}