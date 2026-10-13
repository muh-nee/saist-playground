package com.example.ai

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

data class AssistantConfig(
    val model: String,
    val temperature: Double,
    val systemPrompt: String,
)

@RestController
class ConfigController(private val mapper: ObjectMapper) {

    @Value("\${app.system-prompt}")
    private lateinit var systemPrompt: String

    @GetMapping("/api/assistant/config")
    fun config(): ResponseEntity<String> {
        val cfg = AssistantConfig("gpt-4o", 0.2, systemPrompt)
        // sink: DTO with systemPrompt field serialized to JSON response
        return ResponseEntity.ok(mapper.writeValueAsString(cfg))
    }
}
