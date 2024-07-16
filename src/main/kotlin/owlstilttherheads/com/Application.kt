package owlstilttherheads.com

import io.ktor.server.application.*
import owlstilttherheads.com.controller.configureRouting
import owlstilttherheads.com.plugin.exceptionHandler
import owlstilttherheads.com.plugin.sessionHandler
import owlstilttherheads.com.plugin.settingKoin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    sessionHandler()
    settingKoin()
    configureRouting()
    exceptionHandler()
}
