package com.example.ai.rag

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingStore
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText

class FeedbackIngest(
    private val store: EmbeddingStore<TextSegment>,
    private val embedder: EmbeddingModel,
) {

    suspend fun handle(call: ApplicationCall) {
        val feedback = call.receiveText()
        val segment = TextSegment.from(feedback)
        val embedding = embedder.embed(segment).content()
        // sink: untrusted feedback written to a long-lived LangChain4j embedding store
        store.add(embedding, segment)
        call.respondText("stored")
    }
}
