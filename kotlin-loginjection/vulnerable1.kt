import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("AppLogger")

fun Application.configureRouting() {
    routing {
        get("/login") {
            val username = call.request.queryParameters["username"]
            logger.info("Login attempt for user: $username") // string template injects into message
        }
    }
}
