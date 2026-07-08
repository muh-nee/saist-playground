import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.http.*
import java.io.File

fun Application.configureRouting() {
    val base = File("/var/data").canonicalPath

    routing {
        get("/file") {
            val filename = call.parameters["name"] ?: run {
                call.respond(HttpStatusCode.BadRequest, "Missing filename")
                return@get
            }
            val target = File(base, filename).canonicalFile
            if (!target.path.startsWith(base + File.separator)) {
                call.respond(HttpStatusCode.Forbidden, "Invalid path")
                return@get
            }
            call.respondText(target.readText())
        }
    }
}
