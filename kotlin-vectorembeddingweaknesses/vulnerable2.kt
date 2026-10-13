package com.example.ai.rag

import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class HtmlDoc(val url: String, val html: String)

@RestController
class HelpdeskIngest(private val vectorStore: VectorStore) {

    @PostMapping("/kb/html")
    fun ingest(@RequestBody doc: HtmlDoc): Map<String, String> {
        // sink: user-supplied HTML (potentially a Pinecone-backed VectorStore) stored verbatim
        val d = Document(doc.html, mapOf("source" to doc.url))
        vectorStore.add(listOf(d))
        return mapOf("status" to "indexed", "url" to doc.url)
    }
}
