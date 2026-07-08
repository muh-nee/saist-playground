import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// Vulnerable: user-controlled Ktor parameter passed directly to respondRedirect
fun Application.configureRouting() {
    routing {
        post("/login") {
            val username = call.parameters["username"] ?: ""
            val password = call.parameters["password"] ?: ""

            if (authenticate(username, password)) {
                val next = call.parameters["next"] ?: "/"
                // VULNERABLE: user-controlled redirect destination
                call.respondRedirect(next)
            } else {
                call.respond(io.ktor.http.HttpStatusCode.Unauthorized, "Unauthorized")
            }
        }
    }
}

fun authenticate(username: String, password: String): Boolean {
    return username == "admin" && password == "secret"
}
