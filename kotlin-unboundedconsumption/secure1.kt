package com.example.ai

import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel
import java.time.Duration

object SummarizerModule {

    // safe: both maxTokens and HTTP timeout configured on the LangChain4j client
    val model: ChatLanguageModel = OpenAiChatModel.builder()
        .apiKey(System.getenv("OPENAI_API_KEY"))
        .modelName("gpt-4o-mini")
        .maxTokens(512)
        .timeout(Duration.ofSeconds(20))
        .build()

    fun summarize(doc: String): String = model.generate("Summarize:\n$doc")
}
