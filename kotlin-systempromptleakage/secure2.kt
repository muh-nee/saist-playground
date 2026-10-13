package com.example.ai

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

data class AssistantConfig(
    val model: String,
    val temperature: Double,
    @get:JsonIgnore val systemPrompt: String,
) {
    val systemPromptSha256: String
        get() = java.security.MessageDigest.getInstance("SHA-256")
            .digest(systemPrompt.toByteArray()).joinToString("") { "%02x".format(it) }
}

@RestController
class ConfigController(private val mapper: ObjectMapper) {

    @Value("\${app.system-prompt}")
    private lateinit var systemPrompt: String

    @GetMapping("/api/assistant/config")
    fun config(): ResponseEntity<String> {
        // safe: raw prompt excluded via @JsonIgnore; response exposes only a hash for integrity
        val cfg = AssistantConfig("gpt-4o", 0.2, systemPrompt)
        return ResponseEntity.ok(mapper.writeValueAsString(cfg))
    }
}
