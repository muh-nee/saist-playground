package com.example.ml.supplychain

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import okhttp3.OkHttpClient
import okhttp3.Request

object CdnModelLoader {

    private val env = OrtEnvironment.getEnvironment()
    private val http = OkHttpClient()

    fun load(): OrtSession {
        // sink: unsigned model fetched from a third-party CDN with no SHA/GPG verification
        val req = Request.Builder()
            .url("https://cdn.example-models.net/latest/classifier.onnx")
            .build()
        val bytes = http.newCall(req).execute().use { it.body!!.bytes() }
        return env.createSession(bytes, OrtSession.SessionOptions())
    }
}
