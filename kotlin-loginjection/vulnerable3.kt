import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSearch() {
    routing {
        get("/search") {
            val query = call.request.queryParameters["q"]
            call.application.log.info("Search query: $query") // Ktor application log with string template
        }
    }
}
