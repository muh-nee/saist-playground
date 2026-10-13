package com.example.ml

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respondText

class OnnxLoader {

    private val env = OrtEnvironment.getEnvironment()

    suspend fun handle(call: ApplicationCall) {
        val params = call.receiveParameters()
        val path = params["modelPath"] ?: error("modelPath required")
        // sink: ONNX session created from user-controlled filesystem path, no containment or hash
        val session: OrtSession = env.createSession(path, OrtSession.SessionOptions())
        call.respondText("ok: ${session.inputInfo.keys}")
    }
}
