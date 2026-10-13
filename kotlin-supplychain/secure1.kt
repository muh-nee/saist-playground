package com.example.ml.supplychain

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import java.security.MessageDigest

class HuggingFaceLoader {

    private val env = OrtEnvironment.getEnvironment()
    private val http = HttpClient(CIO)

    // developer-hardcoded (repo, commit SHA, SHA-256 of model.onnx) tuples
    private data class Pin(val repo: String, val revision: String, val sha256: String)

    private val pins = mapOf(
        "sentiment-v3" to Pin(
            repo = "example/sentiment-onnx",
            revision = "c3a1f0b9e4d7a6b2f8e5c4d3b2a1908877665544",
            sha256 = "2b7d9c8e1f0a4b5c6d7e8f90112233445566778899aabbccddeeff0011223344",
        ),
    )

    suspend fun load(name: String): OrtSession {
        val pin = pins[name] ?: throw SecurityException("model not pinned: $name")
        val url = "https://huggingface.co/${pin.repo}/resolve/${pin.revision}/model.onnx"
        val bytes: ByteArray = http.get(url).readBytes()

        val actual = MessageDigest.getInstance("SHA-256").digest(bytes)
            .joinToString("") { "%02x".format(it) }
        require(actual == pin.sha256) { "integrity check failed for $name" }

        // safe: fetched from a pinned commit and SHA-256 verified before loading
        return env.createSession(bytes, OrtSession.SessionOptions())
    }
}
