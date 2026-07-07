import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("AppLogger")

fun Application.configureRouting() {
    routing {
        get("/login") {
            val username = call.request.queryParameters["username"]
            logger.info("login_attempt for user: {}", username) // {} = structured field; message is constant
        }
    }
}
