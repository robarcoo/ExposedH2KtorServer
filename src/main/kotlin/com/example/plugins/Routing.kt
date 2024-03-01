package com.example.plugins

import com.example.routes.candidateRouting
import com.example.routes.companyRouting
import com.example.routes.vacancyRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    routing {
        companyRouting()
        vacancyRouting()
        candidateRouting()
    }
}
