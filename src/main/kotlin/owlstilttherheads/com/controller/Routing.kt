package owlstilttherheads.com.controller

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import owlstilttherheads.com.usecase.post.GetPostsUsecase

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }
    val getPostsUsecase by inject<GetPostsUsecase>()

    routing {
        get("/posts") {
            val posts = getPostsUsecase.handle()

            call.respond(status = HttpStatusCode.OK, posts)
        }
    }
}
