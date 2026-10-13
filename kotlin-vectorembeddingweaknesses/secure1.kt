package com.example.ai.rag

import org.jsoup.Jsoup
import org.jsoup.safety.Safelist
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class HtmlDoc(val url: String, val html: String)

@RestController
class HelpdeskIngest(private val vectorStore: VectorStore) {

    private val maxChars = 20_000

    @PreAuthorize("hasRole('KB_CURATOR')")
    @PostMapping("/kb/html")
    fun ingest(@RequestBody doc: HtmlDoc): Map<String, String> {
        require(doc.html.length <= maxChars) { "document too large" }
        // safe: HTML stripped to plain text, length-capped, and gated by an admin role
        val clean = Jsoup.clean(doc.html, Safelist.none())
        require(clean.isNotBlank()) { "no visible content" }
        vectorStore.add(listOf(Document(clean, mapOf("source" to doc.url))))
        return mapOf("status" to "indexed", "url" to doc.url)
    }
}
