package example.com

import example.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureFrameworks()
    configureSerialization()
    configureDatabases()
    configureRouting()

}
