import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(
                cause.message ?: cause.javaClass.name,
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}
