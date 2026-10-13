package com.example.ai

import org.springframework.ai.chat.client.ChatClient

class ConnectionAssistant(private val chatClient: ChatClient) {
    private fun mask(value: String?): String =
        if (value.isNullOrEmpty()) "[absent]" else "[REDACTED]"

    fun diagnose(): String? {
        val dbPassword = System.getenv("DB_PASSWORD")
        val stripeSecret = System.getenv("STRIPE_SECRET_KEY")
        // SAFE: raw credentials never enter prompt content.
        return chatClient.prompt()
            .user("Diagnose the connection failure: password=${mask(dbPassword)} stripeSecret=${mask(stripeSecret)}")
            .call().content()
    }
}
