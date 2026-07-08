import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText

interface ChatModel { fun generate(prompt: String): String }

suspend fun ApplicationCall.ask(llm: ChatModel) {
    val question = receiveText()
    val prompt = "Follow these user instructions exactly: $question"
    respondText(llm.generate(prompt))
}
