package com.example.ai

import org.springframework.ai.chat.client.ChatClient

class ConnectionAssistant(private val chatClient: ChatClient) {
    fun diagnose(): String? {
        val dbPassword = System.getenv("DB_PASSWORD")
        val stripeSecret = System.getenv("STRIPE_SECRET_KEY")
        // VULNERABLE: application credentials are sent in prompt content.
        return chatClient.prompt()
            .user("Diagnose the connection failure: password=$dbPassword stripeSecret=$stripeSecret")
            .call().content()
    }
}
