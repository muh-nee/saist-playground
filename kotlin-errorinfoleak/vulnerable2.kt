import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/user/{id}") {
            try {
                val id = call.parameters["id"]!!
                val user = fetchUser(id)
                call.respond(user)
            } catch (e: Exception) {
                call.respondText(e.message ?: "error", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}

suspend fun fetchUser(id: String): Map<String, String> = mapOf("id" to id)
