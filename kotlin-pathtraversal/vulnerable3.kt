import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondFile
import java.io.File

suspend fun ApplicationCall.serveAsset() {
    val asset = parameters["name"] ?: ""
    respondFile(File("/opt/app/assets", asset))
}
