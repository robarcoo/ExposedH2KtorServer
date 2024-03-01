package com.example.repository

import com.example.dto.*
import com.example.models.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction

class CandidateRepository {

    fun findAllCandidates() : List <CandidateDto> = transaction {
        Candidate
            .all()
            .with(Candidate::candidateInfo, Candidate::education, Candidate::jobExperience)
            .map {
                getCandidateDto(it)
            }
    }

    fun getAllCandidateInfo(id : Int) : CandidateInfoDto = transaction {
        CandidateInfo.findById(id)
            ?.load(CandidateInfo::contacts)
            ?.let {
                getContacts(it.candidateId.value)?.let { it1 ->
                    CandidateInfoDto(
                        it.candidateId.value,
                        it.name,
                        it.profession,
                        it.sex,
                        it.birthDate,
                        it1,
                        it.relocation
                    )
                }

            }

    }!!

    private fun getContacts(id : Int) : ContactsDto? =  transaction {
        Contact.findById(id)?.let {
          ContactsDto(
              it.candidateId.value,
              it.email,
              it.phone
          )
        }
    }

    fun getEducation(id: Int) : List<EducationDto>? = transaction {
        Candidate.findById(id)?.let { candidate ->
            candidate.education.map {
                EducationDto(
                    it.candidateId.value,
                    it.type,
                    it.yearStart,
                    it.yearEnd,
                    it.description
                )
            }
        }
    }

    fun getExperience(id: Int) : JobExperienceDto? = transaction {
        Experience.findById(id)?.let {
            JobExperienceDto(
                it.candidateId.value,
                it.dateStart,
                it.dateEnd,
                it.companyName,
                it.description
            )
        }
    }

    private fun getCandidateDto(it : Candidate) : CandidateDto = CandidateDto(
            it.id.value,
            getAllCandidateInfo(it.id.value),
            it.education.map { education ->
                EducationDto(
                    education.candidateId.value,
                    education.type,
                    education.yearStart,
                    education.yearEnd,
                    education.description
                )
            },
            it.jobExperience.map { experience ->
                JobExperienceDto(
                    experience.candidateId.value,
                    experience.dateStart,
                    experience.dateEnd,
                    experience.companyName,
                    experience.description
                )
            },
            it.freeForm
        )

    fun getCandidate(id: Int) : CandidateDto? = transaction {
        Candidate.findById(id)?.let {
            getCandidateDto(it)
        }
    }

    fun create(candidate: CandidateDto) : Int {
        val id = createCandidate(candidate)
        createCandidateInfo(id, candidate.candidateInfo)
        createContacts(id, candidate.candidateInfo.contacts)
        createEducation(id, candidate.education)
        createExperience(id, candidate.jobExperience)

        return id
    }



    private fun createCandidate(candidate: CandidateDto) : Int = transaction {
        Candidate.new {
            freeForm = candidate.freeForm

        }.id.value
    }

    private fun createContacts(id: Int, candidate: ContactsDto) : Unit = transaction {
        Contact.new {
            candidateId = EntityID(id, Candidates)
            email = candidate.email
            phone = candidate.phone
        }
    }

    private fun createCandidateInfo(id : Int, candidate: CandidateInfoDto) {
        transaction {
            CandidateInfo.new {
              candidateId = EntityID(id, Candidates)
                name = candidate.name
                profession = candidate.profession
                sex = candidate.sex
                birthDate = candidate.birthDate
                relocation = candidate.relocation
            }
        }
    }

    private fun createEducation(id: Int, education: List<EducationDto>) {
        transaction {
            education.forEach { education ->
                Education.new {
                    candidateId = EntityID(id, Candidates)
                    type = education.type
                    yearStart = education.yearStart
                    yearEnd = education.yearEnd
                    description = education.description
                }
            }
        }
    }

    private fun createExperience(id: Int, experience: List<JobExperienceDto>) {
        transaction {
            experience.forEach { experience ->
                Experience.new {
                    candidateId = EntityID(id, CandidateInfos)
                    dateStart = experience.dateStart
                    dateEnd = experience.dateEnd
                    companyName = experience.companyName
                    description = experience.description
                }
            }
        }
    }

    fun deleteCandidate(id : Int) : Unit = transaction {
        Candidate.findById(id)?.delete()
    }

    fun updateCandidate(id: Int, newCandidate : CandidateDto) : Unit = transaction {
        Candidate.findById(id)?.let {
            updateInfo(id, it.candidateInfo.first(), newCandidate.candidateInfo)
            updateEducation(id, it.education, newCandidate.education)
            updateExperience(id, it.jobExperience, newCandidate.jobExperience)
            it.freeForm = newCandidate.freeForm
        }
    }

    private fun updateInfo(id: Int, it: CandidateInfo, newInfo : CandidateInfoDto) : Unit = transaction {
        it.name = newInfo.name
        it.profession = newInfo.profession
        it.sex = newInfo.sex
        it.birthDate = newInfo.birthDate
        updateContacts(id, it.contacts.first(), newInfo.contacts)
    }

    private fun updateContacts(id : Int, it : Contact, newContacts : ContactsDto) : Unit = transaction{
        it.email = newContacts.email
        it.phone = newContacts.phone
    }

    private fun updateEducation(id: Int, it: SizedIterable<Education>, newEducation: List<EducationDto>) : Unit = transaction{
        it.zip(newEducation)
        { it, new ->
            it.type = new.type
            it.yearStart = new.yearStart
            it.yearEnd = new.yearEnd
            it.description = new.description
        }
    }

    private fun updateExperience(id: Int, it: SizedIterable<Experience>, newExperience: List<JobExperienceDto>) : Unit = transaction {
        it.zip(newExperience)
        { it, new ->
            it.dateStart = new.dateStart
            it.dateEnd = new.dateEnd
            it.companyName = new.companyName
            it.description = new.description
        }
    }
}