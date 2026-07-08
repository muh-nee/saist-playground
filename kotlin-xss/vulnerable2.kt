import io.ktor.http.ContentType
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondText

suspend fun ApplicationCall.profile() {
    val bio = parameters["bio"] ?: ""
    respondText("<div class=\"bio\">$bio</div>", ContentType.Text.Html)
}
