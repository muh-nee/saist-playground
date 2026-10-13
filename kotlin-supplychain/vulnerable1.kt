package com.example.ml.supplychain

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes

class HuggingFaceLoader {

    private val env = OrtEnvironment.getEnvironment()
    private val http = HttpClient(CIO)

    suspend fun loadLatest(repo: String): OrtSession {
        // sink: HuggingFace model downloaded off `main` with no revision pin and no checksum
        val url = "https://huggingface.co/$repo/resolve/main/model.onnx"
        val bytes: ByteArray = http.get(url).readBytes()
        return env.createSession(bytes, OrtSession.SessionOptions())
    }
}
