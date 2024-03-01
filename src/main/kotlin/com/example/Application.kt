package com.example

import com.example.db.CandidateInitializer
import com.example.db.CompanyVacancyInitializer
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory



    fun main() {
        val h2ConnectionString = "jdbc:h2:file:C:/Users/ps/test1"
        Database.connect(h2ConnectionString, driver = "org.h2.Driver")

        CompanyVacancyInitializer.addData()
        CandidateInitializer.addData()

        embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
    }



fun Application.module() {
    configureSerialization()
    configureRouting()
}