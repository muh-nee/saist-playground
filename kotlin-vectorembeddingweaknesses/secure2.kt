package com.example.ai.rag

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingStore
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

class TrustedSourceIngest(
    private val store: EmbeddingStore<TextSegment>,
    private val embedder: EmbeddingModel,
) {
    private val allowedHosts = setOf("docs.example.com", "handbook.internal")
    private val http = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .followRedirects(HttpClient.Redirect.NEVER)
        .build()

    fun ingestFromUrl(sourceUrl: String) {
        val uri = URI.create(sourceUrl)
        require(uri.scheme == "https" && uri.userInfo == null) { "HTTPS source required" }
        require(uri.host in allowedHosts && (uri.port == -1 || uri.port == 443)) { "untrusted source" }
        val request = HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(10)).GET().build()
        val response = http.send(request, HttpResponse.BodyHandlers.ofInputStream())
        val content = response.body().use { body ->
            require(response.statusCode() == 200) { "source did not return content" }
            val bytes = body.readNBytes(50_001)
            require(bytes.size in 1..50_000) { "content size out of range" }
            String(bytes, Charsets.UTF_8)
        }
        // SAFE: content is fetched from the trusted HTTPS source, not supplied by the caller.
        // Redirects cannot substitute a different origin for the allowlisted source.
        val segment = TextSegment.from(content, dev.langchain4j.data.document.Metadata.from("source", sourceUrl))
        store.add(embedder.embed(segment).content(), segment)
    }
}
