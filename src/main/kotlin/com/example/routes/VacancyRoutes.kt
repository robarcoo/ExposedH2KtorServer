package com.example.routes

import com.example.repository.VacancyRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.vacancyRouting() {
    val repository = VacancyRepository()
    route("/vacancy") {
        get {
            call.respond(repository.findAllVacancies())
        }

        get ("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText("", status = HttpStatusCode.OK)
            val vacancy = repository.findFullVacancy(id.toInt()) ?: return@get call.respondText("", status = HttpStatusCode.OK)
            call.respond(vacancy)
        }

        post {
            call.respond(repository.createVacancy(call.receive()))
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("", status = HttpStatusCode.OK)
            call.respond(repository.deleteVacancy(id.toInt()))
        }

        put("{id?}") {
            val id = call.parameters["id"] ?: return@put call.respondText("", status = HttpStatusCode.OK)
            call.respond(repository.updateVacancy(id.toInt(), call.receive()))
        }


    }

}