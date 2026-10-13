package com.example.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.stereotype.Service

@Service
class ChatService(builder: ChatClient.Builder) {
    private val chatClient: ChatClient = builder.build()

    fun answer(userMessage: String): String? =
        chatClient.prompt(userMessage)
            .options(OpenAiChatOptions.builder()
                .model("gpt-4o")
                .maxTokens(512)
                .build()) // SAFE: per-request options include a positive output-token limit.
            .call()
            .content()
}
