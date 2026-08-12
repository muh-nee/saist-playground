import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI

suspend fun processTask(task: String): String {
	val client = OpenAI(token = System.getenv("OPENAI_API_KEY"))
	val request = ChatCompletionRequest(
		model = ModelId("gpt-4o-mini"),
		messages = listOf(ChatMessage(role = ChatRole.User, content = task))
	)
	val completion = client.chatCompletion(request)
	val output = completion.choices.first().message.content.orEmpty()
	val clean = output.replace(Regex("\\x1B\\[[0-9;]*m"), "")
	println(clean)
	return clean
}
