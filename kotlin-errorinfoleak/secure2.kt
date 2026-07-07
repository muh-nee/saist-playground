import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

fun Application.configureSafeRouting() {
    val logger = LoggerFactory.getLogger("SafeRouting")
    routing {
        get("/user/{id}") {
            try {
                val id = call.parameters["id"]!!
                val user = fetchUser(id)
                call.respond(user)
            } catch (e: Exception) {
                logger.error("getUser failed", e)
                call.respondText("internal server error", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}
