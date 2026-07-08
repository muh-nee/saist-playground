interface ChatModel { fun generate(prompt: String): String }

fun summarize(chatModel: ChatModel, userTopic: String): String {
    val prompt = "You are a %s expert. Summarize the latest news.".format(userTopic)
    return chatModel.generate(prompt)
}
