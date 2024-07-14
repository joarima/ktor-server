package owlstilttherheads.com.controller

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import owlstilttherheads.com.usecase.post.GetPostWithIdUsecase
import owlstilttherheads.com.usecase.post.GetPostsUsecase
import java.util.*

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }
    val getPostsUsecase by inject<GetPostsUsecase>()
    val getPostWithIdUsecase by inject<GetPostWithIdUsecase>()

    routing {
        get("/posts") {
            val posts = getPostsUsecase.handle()

            call.respond(HttpStatusCode.OK, posts)
        }

        get("/post/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                "id not specified."
            )
            val post =
                getPostWithIdUsecase.handle(UUID.fromString(id))
                    ?: return@get call.respond(status = HttpStatusCode.NotFound, "post with id $id not found.")
            call.respond(HttpStatusCode.OK, post)
        }
    }
}
