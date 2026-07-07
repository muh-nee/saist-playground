import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/items") {
            val page = call.parameters["page"]?.toInt() ?: 0
            val size = call.parameters["size"]?.toInt() ?: 10
            val offset = page * size  // may overflow Int if page * size > Int.MAX_VALUE
            call.respondText("offset=$offset")
        }
    }
}
