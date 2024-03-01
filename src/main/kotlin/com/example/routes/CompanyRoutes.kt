package com.example.routes

import com.example.repository.CompanyRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.companyRouting() {
    val repository = CompanyRepository()

    route("/company") {
        get {
            call.respond(repository.findAllCompanies())
        }
        get ("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText("", status = HttpStatusCode.OK)
            val company = repository.findCompany(id.toInt()) ?: return@get call.respondText("", status = HttpStatusCode.OK)
            call.respond(company)
        }
        post {
            call.respond(repository.createCompany(call.receive()))
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("", status = HttpStatusCode.OK)
            call.respond(repository.deleteCompany(id.toInt()))
        }

        put("{id?}") {
            val id = call.parameters["id"] ?: return@put call.respondText("", status = HttpStatusCode.OK)
            call.respond(repository.updateCompany(id.toInt(), call.receive()))
        }

    }


}