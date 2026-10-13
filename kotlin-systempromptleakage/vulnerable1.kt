package com.example.ai

import dev.langchain4j.data.message.SystemMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.ChatLanguageModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SupportBot(private val model: ChatLanguageModel) {

    @Value("\${app.system-prompt}")
    private lateinit var systemPrompt: String

    @PostMapping("/chat")
    fun chat(@RequestParam q: String): ResponseEntity<Map<String, String>> {
        return try {
            val answer = model.generate(SystemMessage.from(systemPrompt), UserMessage.from(q))
                .content().text()
            ResponseEntity.ok(mapOf("answer" to answer))
        } catch (e: Exception) {
            // sink: developer-authored system prompt leaks in error response body
            ResponseEntity.status(500).body(
                mapOf("error" to e.message.orEmpty(), "systemPrompt" to systemPrompt)
            )
        }
    }
}
