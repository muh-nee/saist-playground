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
                .build()) // VULNERABLE: per-request options omit the output-token limit.
            .call()
            .content()
}
