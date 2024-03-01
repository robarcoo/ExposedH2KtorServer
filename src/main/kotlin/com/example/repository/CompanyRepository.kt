package com.example.repository

import com.example.dto.CompanyDto
import com.example.dto.VacancyDto
import com.example.models.Companies
import com.example.models.Company
import com.example.models.Vacancies.companyId
import com.example.models.Vacancy
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction

class CompanyRepository {

    fun findAllCompanies(): List<CompanyDto> = transaction {
        Company
            .all()
            .with(Company::vacancies)
            .map {
                CompanyDto(
                    it.id.value,
                    it.name,
                    it.activity,
                    it.vacancies.map { vacancy ->
                        VacancyDto(
                            vacancy.id.value,
                            vacancy.name,
                            vacancy.level,
                            vacancy.salary,
                            it.id.value,
                            vacancy.description
                        )
                    },
                    it.contacts
                )
            }
    }

    fun findCompany(id: Int): CompanyDto? = transaction {
        Company.findById(id)
            ?.load(Company::vacancies)
            ?.let {
                CompanyDto(
                    it.id.value,
                    it.name,
                    it.activity,
                    it.vacancies.map { vacancy ->
                        VacancyDto(
                            vacancy.id.value,
                            vacancy.name,
                            vacancy.level,
                            vacancy.salary,
                            it.id.value,
                            vacancy.description
                        )
                    },
                    it.contacts
                )
            }
    }

    fun createCompany(company: CompanyDto): Int = transaction {
        Company.new {
            name = company.name
            activity = company.activity
            contacts = company.contacts
        }.id.value
    }

    fun deleteCompany(id: Int) : Unit = transaction {
        Company.findById(id)?.delete()
    }

    fun updateCompany(id : Int, newCompany : CompanyDto) : Unit = transaction {
        Company.findById(id)?.let {
            it.name = newCompany.name
            it.activity = newCompany.activity
            it.contacts = newCompany.contacts
        }
    }

    fun addVacancy(id: Int, vacancy : VacancyDto) {
        transaction {
            Company.findById(id)?.let {company ->
                SizedCollection(
                    company.vacancies + vacancy.let {
                        Vacancy.new {
                            name = vacancy.profession
                            level = vacancy.level
                            salary = vacancy.salary
                            companyId = EntityID(id, Companies)
                        }
                    }
                )
            }
        }
    }
}
