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
            val env = System.getenv("DB_USER_NAME")
            println(env)
            val posts = getPostsUsecase.handle()
            println(posts)

            call.respond(status = HttpStatusCode.OK, posts)
        }
    }
}
