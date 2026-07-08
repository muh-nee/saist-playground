data class ChatMessage(val role: String, val content: String)
interface ChatClient { fun complete(messages: List<ChatMessage>): String }

fun chat(client: ChatClient, message: String): String {
    val messages = listOf(
        ChatMessage("system", "You are a helpful assistant."),
        ChatMessage("user", message),
    )
    return client.complete(messages)
}
