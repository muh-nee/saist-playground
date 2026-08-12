import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSummaryRoutes() {
	val openAiClient = OpenAI(token = System.getenv("OPENAI_API_KEY"))
	routing {
		get("/summary") {
			val request = ChatCompletionRequest(
				model = ModelId("gpt-4o-mini"),
				messages = listOf(ChatMessage(role = ChatRole.User, content = "Summarize the latest AI news in Markdown."))
			)
			val completion = openAiClient.chatCompletion(request)
			val content = completion.choices.first().message.content.orEmpty()
			call.response.headers.append("Content-Security-Policy", "default-src 'self'; img-src *")
			call.respond(mapOf("content" to content))
		}
	}
}
