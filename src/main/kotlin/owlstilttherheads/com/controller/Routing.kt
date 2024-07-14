package owlstilttherheads.com.controller

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import org.koin.ktor.ext.inject
import owlstilttherheads.com.usecase.post.*
import java.util.*

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }
    val getPostsUsecase by inject<GetPostsUsecase>()
    val getPostWithIdUsecase by inject<GetPostWithIdUsecase>()
    val updatePostsUsecase by inject<UpdatePostUsecase>()
    val createPostUsecase by inject<CreatePostUsecase>()

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

        patch("/post/{id}") {
            val id = call.parameters["id"] ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                "id not specified."
            )
            val request = call.receive<UpdateRequest>()
            updatePostsUsecase.handle(request.toDto(UUID.fromString(id)))
            call.respond(HttpStatusCode.OK)
        }

        post("/post") {
            val request = call.receive<CreateRequest>()
            createPostUsecase.handle(request.toDto())
            call.respond(HttpStatusCode.Created)
        }
    }
}

@Serializable
data class UpdateRequest(
    val content: JsonElement,
    val isOpen: Boolean
) {
    fun toDto(id: UUID): UpdatePostDto {
        return UpdatePostDto(
            id = id,
            content = content,
            isOpen = isOpen
        )
    }
}

@Serializable
data class CreateRequest(
    val content: JsonElement,
    val isOpen: Boolean
) {
    fun toDto(): CreatePostDto {
        return CreatePostDto(
            content = content,
            isOpen = isOpen
        )
    }
}
