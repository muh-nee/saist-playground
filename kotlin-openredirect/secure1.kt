import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.net.URI

// Safe: parse URI and verify no scheme/authority before redirecting
fun Application.configureRouting() {
    routing {
        post("/login") {
            val username = call.parameters["username"] ?: ""
            val password = call.parameters["password"] ?: ""

            if (authenticate(username, password)) {
                val next = call.parameters["next"] ?: "/dashboard"
                val safeNext = runCatching { URI.create(next) }
                    .getOrNull()
                    ?.takeIf { it.scheme == null && it.authority == null }
                    ?.let { next }
                    ?: "/dashboard"
                call.respondRedirect(safeNext)
            } else {
                call.respond(io.ktor.http.HttpStatusCode.Unauthorized, "Unauthorized")
            }
        }
    }
}

fun authenticate(username: String, password: String): Boolean {
    return username == "admin" && password == "secret"
}
