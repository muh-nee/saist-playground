package com.example.ai.rag

import io.qdrant.client.QdrantClient
import io.qdrant.client.PointIdFactory.id
import io.qdrant.client.ValueFactory.value
import io.qdrant.client.VectorsFactory.vectors
import io.qdrant.client.grpc.Points.PointStruct
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

data class IngestRequest(val note: String)

@RestController
class NotesIngest(private val qdrant: QdrantClient, private val embedder: TextEmbedder) {

    @PostMapping("/notes")
    fun ingest(@RequestBody req: IngestRequest): String {
        val vector = embedder.embed(req.note)
        val point = PointStruct.newBuilder()
            .setId(id(UUID.randomUUID()))
            .setVectors(vectors(vector))
            .putAllPayload(mapOf("text" to value(req.note)))
            .build()
        // sink: raw user content stored in a shared Qdrant collection — RAG poisoning
        qdrant.upsertAsync("notes", listOf(point)).get()
        return "ok"
    }
}

interface TextEmbedder { fun embed(text: String): FloatArray }
