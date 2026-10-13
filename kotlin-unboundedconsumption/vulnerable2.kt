package com.example.ai

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.flow.collect

class StreamingAssistant(private val client: OpenAI) {

    suspend fun stream(prompt: String): String {
        val sb = StringBuilder()
        val req = ChatCompletionRequest(
            model = ModelId("gpt-4o"),
            messages = listOf(ChatMessage(role = ChatRole.User, content = prompt))
            // no maxTokens set and no per-chunk cap — downstream buffer grows unbounded
        )
        // sink: unbounded streaming loop appends every chunk with no size ceiling
        client.chatCompletions(req).collect { chunk ->
            chunk.choices.forEach { sb.append(it.delta?.content.orEmpty()) }
        }
        return sb.toString()
    }
}
