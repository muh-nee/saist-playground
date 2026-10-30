package com.example.ai.legal

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class LegalQuestion(val jurisdiction: String, val question: String)

private const val DISCLAIMER =
    "This response is AI-generated and is not legal advice. Consult a licensed attorney before acting."

@RestController
class LegalAssistantController(
    private val chatClient: ChatClient,
    private val caseLaw: VectorStore,
) {

    @PostMapping("/legal/answer")
    fun answer(@RequestBody q: LegalQuestion): ResponseEntity<Map<String, Any>> {
        val sources: List<Document> = caseLaw.similaritySearch(q.question).orEmpty()
        val context = sources.joinToString("\n") { "- ${it.metadata["citation"]}: ${it.text}" }

        val reply = chatClient.prompt()
            .user(
                "Jurisdiction: ${q.jurisdiction}. Only answer using the sources below; " +
                    "if not covered, say you don't know.\n$context\nQuestion: ${q.question}"
            )
            .call()
            .content()

        // safe: grounded answer, citations returned, and disclaimer attached
        return ResponseEntity.ok(
            mapOf(
                "answer" to reply,
                "sources" to sources.map { it.metadata["citation"] },
                "disclaimer" to DISCLAIMER,
            )
        )
    }
}

