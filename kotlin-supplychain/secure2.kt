package com.example.ml.supplychain

import com.anthropic.client.AnthropicClient
import com.anthropic.models.messages.MessageCreateParams
import com.anthropic.models.messages.Model

class DependencyAssistant(private val anthropic: AnthropicClient) {

    // developer-maintained allowlist of vetted Maven coordinates (groupId:artifactId:version)
    private val allowlist = setOf(
        "org.apache.commons:commons-lang3:3.14.0",
        "com.fasterxml.jackson.core:jackson-databind:2.17.1",
        "org.jsoup:jsoup:1.17.2",
    )

    fun installSuggestedPackage(task: String): Process {
        val params = MessageCreateParams.builder()
            .model(Model.CLAUDE_3_5_SONNET_20241022)
            .maxTokens(256)
            .addUserMessage(
                "Pick exactly one coordinate from this allowlist that best fits: " +
                    "${allowlist.joinToString(", ")}. Task: $task"
            )
            .build()
        val coord = anthropic.messages().create(params)
            .content().first().asText().text().trim()

        // safe: suggestion must match the vetted allowlist before any shell execution
        require(coord in allowlist) { "coordinate not on allowlist: $coord" }
        return ProcessBuilder("mvn", "dependency:get", "-Dartifact=$coord")
            .inheritIO()
            .start()
    }
}
