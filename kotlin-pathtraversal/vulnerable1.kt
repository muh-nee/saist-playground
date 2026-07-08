import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.http.*
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/file") {
            val filename = call.parameters["name"] ?: ""
            val content = File("/var/data/$filename").readText()
            call.respondText(content)
        }

        get("/download") {
            val name = call.parameters["filename"] ?: ""
            val header = call.request.headers["X-Filename"] ?: name
            val file = File("/uploads", header)
            call.respondBytes(file.readBytes(), ContentType.Application.OctetStream)
        }
    }
}
