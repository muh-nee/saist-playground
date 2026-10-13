package com.example.ml

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respondText
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest

class OnnxLoader {

    private val env = OrtEnvironment.getEnvironment()
    private val allowedRoot: Path = Path.of("/opt/models").toRealPath()

    // developer-maintained map of permitted model names to pinned SHA-256 digests
    private val expected = mapOf(
        "sentiment-v3" to "9f8c0ab2a94c2d7f8e3c1a4bf6e5d2c3b7a0e9d8c7b6a5a4b3c2d1e0f9a8b7c6",
    )

    suspend fun handle(call: ApplicationCall) {
        val name = call.receiveParameters()["name"] ?: error("name required")
        val digest = expected[name] ?: throw SecurityException("unknown model: $name")

        val resolved = allowedRoot.resolve("$name.onnx").normalize().toRealPath()
        require(resolved.startsWith(allowedRoot)) { "path escape rejected" }

        val bytes = Files.readAllBytes(resolved)
        val actual = MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }
        require(actual == digest) { "integrity check failed" }

        // safe: name allowlisted, path confined, SHA-256 verified before session creation
        val session: OrtSession = env.createSession(resolved.toString(), OrtSession.SessionOptions())
        call.respondText("ok: ${session.inputInfo.keys}")
    }
}
