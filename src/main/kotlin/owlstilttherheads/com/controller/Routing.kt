package owlstilttherheads.com.controller

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import org.koin.ktor.ext.inject
import owlstilttherheads.com.domain.exception.BadRequestException
import owlstilttherheads.com.usecase.post.*
import java.util.*

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }
    val getPostsUsecase by inject<GetPostsUsecase>()
    val getPostWithIdUsecase by inject<GetPostWithIdUsecase>()
    val searchPostUsecase by inject<SearchPostUsecase>()
    val updatePostsUsecase by inject<UpdatePostUsecase>()
    val createPostUsecase by inject<CreatePostUsecase>()
    val deletePostUsecase by inject<DeletePostUsecase>()

    routing {
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml")
        get("/posts") {
            val keywords = call.request.queryParameters["q"]
            val posts = if (keywords.isNullOrEmpty()) {
                getPostsUsecase.handle()
            } else {
                searchPostUsecase.handle(keywords)
            }
            call.respond(HttpStatusCode.OK, posts)
        }

        get("/post/{id}") {
            val id = call.parameters["id"] ?: throw BadRequestException("id not specified.")
            val post =
                getPostWithIdUsecase.handle(UUID.fromString(id))
                    ?: throw BadRequestException("post with id $id not found.")
            call.respond(HttpStatusCode.OK, post)
        }

        patch("/post/{id}") {
            val id = call.parameters["id"] ?: throw BadRequestException("id not specified.")
            val request = call.receive<UpdateRequest>()
            updatePostsUsecase.handle(request.toDto(UUID.fromString(id)))
            call.respond(HttpStatusCode.OK)
        }

        post("/post") {
            val request = call.receive<CreateRequest>()
            createPostUsecase.handle(request.toDto())
            call.respond(HttpStatusCode.Created)
        }

        delete("/post/{id}") {
            val id = call.parameters["id"] ?: throw BadRequestException("id not specified.")
            deletePostUsecase.handle(UUID.fromString(id))
            call.respond(HttpStatusCode.OK)
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
