package com.example.ai.legal

import org.springframework.ai.chat.client.ChatClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class LegalQuestion(val jurisdiction: String, val question: String)

@RestController
class LegalAssistantController(private val chatClient: ChatClient) {

    @PostMapping("/legal/answer")
    fun answer(@RequestBody q: LegalQuestion): ResponseEntity<Map<String, String>> {
        val reply = chatClient.prompt()
            .user("In ${q.jurisdiction}, answer this legal question definitively: ${q.question}")
            .call()
            .content()
        // sink: raw LLM legal guidance returned as authoritative with no disclaimer/citations
        return ResponseEntity.ok(mapOf("answer" to reply))
    }
}
