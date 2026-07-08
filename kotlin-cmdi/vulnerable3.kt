import io.ktor.server.application.ApplicationCall

suspend fun ApplicationCall.compress() {
    val target = parameters["path"] ?: ""
    val cmd = listOf("sh", "-c", "gzip $target")
    ProcessBuilder(cmd).start()
}
