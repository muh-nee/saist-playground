import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("SearchLogger")

fun Application.configureSearch() {
    routing {
        get("/search") {
            val query = call.request.queryParameters["q"]
            val clean = query?.filter { it != '\r' && it != '\n' } ?: ""
            logger.info("search_performed query={}", clean) // CRLF filtered; value in structured field
        }
    }
}
