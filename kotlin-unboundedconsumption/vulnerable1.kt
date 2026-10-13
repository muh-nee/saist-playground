package com.example.ai

import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel

object SummarizerModule {

    // sink: builder omits maxTokens — every subsequent generate() can return an unbounded completion
    val model: ChatLanguageModel = OpenAiChatModel.builder()
        .apiKey(System.getenv("OPENAI_API_KEY"))
        .modelName("gpt-4o-mini")
        .build()

    fun summarize(doc: String): String = model.generate("Summarize:\n$doc")
}
