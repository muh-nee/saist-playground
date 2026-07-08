interface ChatModel { fun generate(messages: List<Pair<String, String>>): String }

private val ALLOWED = Regex("^[\\w .,!?-]{1,200}$")

fun ask(chatModel: ChatModel, question: String): String {
    require(ALLOWED.matches(question)) { "invalid input" }
    val messages = listOf(
        "system" to "You answer questions about our product catalog only.",
        "user" to question,
    )
    return chatModel.generate(messages)
}
