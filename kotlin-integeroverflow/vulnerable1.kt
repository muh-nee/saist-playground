import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/process") {
            val value = call.parameters["value"]?.toLong() ?: 0L
            val id = value.toInt()  // silently truncates if value > Int.MAX_VALUE
            call.respondText(id.toString())
        }
    }
}
