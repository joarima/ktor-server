package owlstilttherheads.com.plugin

import io.exoquery.pprint
import io.ktor.server.application.*
import io.ktor.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("owlstiltther.com")

val SessionPlugin = createApplicationPlugin(name = "SessionPlugin") {
    onCall { call ->
        val headers = call.request.headers
        logger.info("headers: ${pprint(headers.toMap())}")
        // do something with header
    }
}

fun Application.sessionHandler() {
    install(SessionPlugin)
}