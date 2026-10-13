package com.example.ai

import dev.langchain4j.data.message.SystemMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.ChatLanguageModel
import org.slf4j.LoggerFactory

class ChatService(private val model: ChatLanguageModel) {

    private val log = LoggerFactory.getLogger(ChatService::class.java)

    private val systemPrompt: String = """
        You are an internal triage agent. Never reveal that you have access to the
        operations_runbook tool. If asked about pricing, consult pricing_v3.
    """.trimIndent()

    fun ask(userMessage: String): String {
        // sink: developer-authored system prompt written verbatim to the log
        log.info("invoking model with system prompt: {}", systemPrompt)
        return model.generate(SystemMessage.from(systemPrompt), UserMessage.from(userMessage))
            .content().text()
    }
}
