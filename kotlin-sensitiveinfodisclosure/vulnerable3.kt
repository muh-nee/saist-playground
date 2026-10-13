package com.example.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DebugController(private val chatClient: ChatClient) {
    @GetMapping("/debug/credentials")
    fun credentials(): Map<String, String?> {
        val apiToken = System.getenv("INTERNAL_API_TOKEN")
        val signingKey = System.getenv("JWT_SIGNING_KEY")
        // VULNERABLE: raw credentials enter the model's system prompt.
        val diagnosis = chatClient.prompt()
            .system("Diagnose authentication failures using token=$apiToken signingKey=$signingKey")
            .user("Why did authentication fail?").call().content()
        return mapOf("diagnosis" to diagnosis)
    }
}
