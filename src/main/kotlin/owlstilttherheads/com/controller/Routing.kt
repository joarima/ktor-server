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
import owlstilttherheads.com.usecase.post.GetPostWithIdUsecase
import owlstilttherheads.com.usecase.post.GetPostsUsecase
import owlstilttherheads.com.usecase.post.UpdatePostDto
import owlstilttherheads.com.usecase.post.UpdatePostUsecase
import owlstilttherheads.com.utils.serializer.UuidSerializer
import java.util.*

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }
    val getPostsUsecase by inject<GetPostsUsecase>()
    val getPostWithIdUsecase by inject<GetPostWithIdUsecase>()
    val updatePostsUsecase by inject<UpdatePostUsecase>()

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
            val request = call.receive<Request>()
            println(request)
            updatePostsUsecase.handle(request.toDto())
        }
    }
}

@Serializable
data class Request(
    @Serializable(with = UuidSerializer::class) val id: UUID,
    val content: JsonElement,
    val isOpen: Boolean
) {
    fun toDto(): UpdatePostDto {
        return UpdatePostDto(
            id = id,
            content = content,
            isOpen = isOpen
        )
    }
}
