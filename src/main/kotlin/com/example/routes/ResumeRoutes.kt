package com.example.routes

import com.example.dto.EducationDto
import com.example.dto.JobExperienceDto
import com.example.repository.CandidateRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.candidateRouting() {
    route("/candidate") {
        val repository = CandidateRepository()
        get {
            call.respond(repository.findAllCandidates())
        }

        post {
            call.respond(repository.create(call.receive()))
        }

        post("{id?}/education"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@post call.respondText("", status = HttpStatusCode.OK)
            call.respond(repository.createEducation(id, listOf(EducationDto(id, "", "", "", ""))))
        }

        post("{id?}/experience"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@post call.respondText("", status = HttpStatusCode.OK)
            call.respond(repository.createExperience(id, listOf(JobExperienceDto(id, "", "", "", ""))))
        }

        get ("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText("", status = HttpStatusCode.OK)
            val candidate = repository.getAllCandidateInfo(id.toInt()) ?: return@get call.respondText("", status = HttpStatusCode.OK)
            call.respond(candidate)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("", status = HttpStatusCode.OK)
            call.respond(repository.deleteCandidate(id.toInt()))
        }

        patch("{id?}") {
            val id = call.parameters["id"] ?: return@patch call.respondText("", status = HttpStatusCode.OK)
            call.respond(repository.updateCandidate(id.toInt(), call.receive()))
        }
    }
}