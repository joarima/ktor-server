package owlstilttherheads.com

import io.ktor.server.application.*
import owlstilttherheads.com.controller.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    settingKoin()
    configureRouting()
    exceptionHandler()
}
