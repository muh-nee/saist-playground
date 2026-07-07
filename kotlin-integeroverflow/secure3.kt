import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/process") {
            val value = call.parameters["value"]?.toLong() ?: 0L
            val id = Math.toIntExact(value)  // throws ArithmeticException if value doesn't fit in Int
            call.respondText(id.toString())
        }
    }
}
