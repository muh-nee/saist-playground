import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureProcessRouting() {
    routing {
        get("/process") {
            try {
                call.respond(runProcess())
            } catch (e: Exception) {
                call.respondText(e.stackTraceToString(), status = HttpStatusCode.InternalServerError)
            }
        }
    }
}

fun runProcess(): Map<String, Any> = mapOf("result" to "ok")
