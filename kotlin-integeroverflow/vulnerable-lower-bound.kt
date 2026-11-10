import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/items") {
            val page = call.parameters["page"]?.toInt() ?: 0
            val size = call.parameters["size"]?.toInt() ?: 10
            val offset = page.toLong() * size.toLong()  // Long arithmetic: no overflow
            if (offset > Int.MAX_VALUE) error("offset too large")
            call.respondText("offset=${offset.toInt()}")
        }
    }
}
