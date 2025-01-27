package owlstilttherheads.com.plugin

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import owlstilttherheads.com.domain.exception.*

fun Application.exceptionHandler() {
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "Bad Request: $cause")
        }
        exception<UnauthorizedException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, cause.message ?: "Unauthorized: $cause")
        }
        exception<NotFoundException> { call, cause ->
            call.respond(HttpStatusCode.NotFound, cause.message ?: "Not Found: $cause")
        }
        exception<InternalServerException> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Internal Server Error: $cause")
        }
        exception<InfrastructureException> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Unknown Error Occurred: $cause")
        }
        // default
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Unknown Internal Server Error: $cause")
        }
    }
}