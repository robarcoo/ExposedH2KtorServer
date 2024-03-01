package com.example.db

import com.example.dto.*
import com.example.models.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.exp

object CandidateInitializer  {

    fun addData() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Candidates,
                CandidateInfos,
                Educations,
                Experiences,
                Contacts)
        }
        val totalCandidates = transaction {
            Candidates.selectAll().count()
        }

        if (totalCandidates > 0) return

        val candidate = CandidateDto( 1,
            CandidateInfoDto(1,
                "Vasiliev Sergei Petrovich",
                "QA",
                "male",
                "30.09.1998",
                ContactsDto(1,
                "72938572843",
                "vspetrovich@pochta.ru"),
                "possible"
                ),
            listOf(EducationDto(1,
                "higher",
                "2017",
                "2021",
                "Mathematics in state university"
                ),
                EducationDto(1,
                    "secondary special",
                    "2013",
                    "2017",
                    "College of informatics"),
                EducationDto(1,
                    "secondary",
                    "2005",
                    "2013",
                    "Lyceum 156")
            ),
            listOf(JobExperienceDto(1,
                "08.2021",
                "04.2022",
                "FinTech",
                "Some fintech company creating a business platform"),
                JobExperienceDto(1,
                    "05.2022",
                    "01.2023",
                    "SoftProm",
                    "Typical galley")
            ),
            "I'm a QA specialist from head to heels. ..."
        )

        transaction {
            SchemaUtils.create(Candidates,
                CandidateInfos,
                Educations,
                Experiences,
                Contacts)

//            Candidates.insert {
//                it[id] = candidate.id
//                it[freeForm] = candidate.freeForm
//            }
//
//            CandidateInfos.insert {
//                it[candidateId] = candidate.id
//                it[name] = candidate.candidateInfo.name
//                it[profession] = candidate.candidateInfo.profession
//                it[sex] = candidate.candidateInfo.sex
//                it[birthDate] = candidate.candidateInfo.birthDate
//                it[relocation] = candidate.candidateInfo.relocation
//            }
//
//            Contacts.insert {
//                it[candidateId] = candidate.id
//                it[phone] = candidate.candidateInfo.contacts.phone
//                it[email] = candidate.candidateInfo.contacts.email
//            }
//
//            candidate.education.forEach { education ->
//                Educations.insert {
//                    it[candidateId] = education.candidateId
//                    it[type] = education.type
//                    it[yearStart] = education.yearStart
//                    it[yearEnd] = education.yearEnd
//                    it[description] = education.description
//                }
//            }
//
//            candidate.jobExperience.forEach {
//                experience ->
//                Experiences.insert {
//                    it[candidateId] = experience.candidateId
//                    it[dateStart] = experience.dateStart
//                    it[dateEnd] = experience.dateEnd
//                    it[companyName] = experience.companyName
//                    it[description] = experience.description
//                }
//            }

        }

    }
}