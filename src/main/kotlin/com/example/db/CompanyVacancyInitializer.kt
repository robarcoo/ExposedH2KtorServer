package com.example.db

import com.example.dto.CompanyDto
import com.example.dto.VacancyDto
import com.example.models.Companies
import com.example.models.Vacancies
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


object CompanyVacancyInitializer {

    fun addData() {

        transaction {
            SchemaUtils.createMissingTablesAndColumns(Companies, Vacancies)
        }

        val totalCompanies = transaction {
            Companies.selectAll().count()
        }

        if (totalCompanies > 0) return

        val companyStorage = mutableListOf(
            CompanyDto(
                1,
                "OOO SuperPay",
                "banking",
                listOf(
                    VacancyDto(1, "QA", "middle", 80000, 1),
                    VacancyDto(2, "PM", "senior", 80000, 1)
                ),
            ),

            CompanyDto(
                2,
                "MTM",
                "banking",
                listOf(VacancyDto(3, "designer", "senior", 150000, 2))
            ),
            CompanyDto(
                3,
                "CryptoSuperGo",
                "banking",
                listOf(VacancyDto(4, "developer", "junior", 90000, 3)),
            ),
            CompanyDto(
                4,
                "PlatiNalogi",
                "public services",
                listOf(
                    VacancyDto(5, "developer", "middle", 150000, 4),
                    VacancyDto(6, "designer", "middle", 100000, 4),
                    VacancyDto(7, "PM", "senior", 160000, 4)
                ),

                ),

            CompanyDto(
                5,
                "NeftGazIkra",
                "public services",
                listOf(VacancyDto(8, "analyst", "junior", 70000, 5)),
            ),

            CompanyDto(
                6,
                "OOO SoftForHomies",
                "IT",
                listOf(
                    VacancyDto(9, "PM", "middle", 100000, 6),
                    VacancyDto(10, "QA", "junior", 60000, 6)
                ),
            ),

            CompanyDto(
                7,
                "MobileGamesPro",
                "IT",
                listOf(VacancyDto(11, "QA", "senior", 130000, 7)),
            ),


            CompanyDto(
                8,
                "FoodsAndGoods",
                "public services",
                listOf(
                    VacancyDto(12, "developer", "junior", 100000, 8),
                    VacancyDto(13, "designer", "middle", 130000, 8),
                    VacancyDto(14, "analyst", "senior", 150000, 8)
                ),
            ),

            CompanyDto(
                9,
                "VseIgry",
                "IT",
                listOf(
                    VacancyDto(15, "developer", "senior", 200000, 9),
                    VacancyDto(16, "designer", "senior", 180000, 9)
                ),
            ),
            CompanyDto(
                10,
                "ItBankingMax",
                "banking",
                listOf(VacancyDto(17, "QA", "senior", 140000, 10)),

                )
        )

        transaction {
            SchemaUtils.create(Companies, Vacancies)

            Companies.batchInsert(companyStorage) {
                this[Companies.id] = it.id
                this[Companies.name] = it.name
                this[Companies.activity] = it.activity
                this[Companies.contacts] = it.contacts
            }

            companyStorage.forEach {
                company ->
                company.vacancies.forEach {
                    vacancy ->
                    Vacancies.insert {
                        it[name] = vacancy.name
                        it[level] = vacancy.level
                        it[salary] = vacancy.salary
                        it[companyId] = vacancy.companyId
                        it[description] = vacancy.description

                    }
                }
            }
        }
    }

}