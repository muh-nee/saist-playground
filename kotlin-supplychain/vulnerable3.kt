package com.example.ml.supplychain

import com.anthropic.client.AnthropicClient
import com.anthropic.models.messages.MessageCreateParams
import com.anthropic.models.messages.Model

class DependencyAssistant(private val anthropic: AnthropicClient) {

    fun installSuggestedPackage(task: String): Process {
        val params = MessageCreateParams.builder()
            .model(Model.CLAUDE_3_5_SONNET_20241022)
            .maxTokens(256)
            .addUserMessage("Suggest exactly one Maven coordinate (groupId:artifactId:version) for: $task")
            .build()
        val coord = anthropic.messages().create(params)
            .content().first().asText().text().trim()

        // sink: LLM-hallucinated Maven coordinate shelled out without any allowlist — slopsquatting
        return ProcessBuilder("mvn", "dependency:get", "-Dartifact=$coord")
            .inheritIO()
            .start()
    }
}
