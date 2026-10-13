package com.example.ai.finance

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

data class Portfolio(val holdings: Map<String, Int>, val goal: String)

class AdvisorHandler(private val client: OpenAI) {

    suspend fun advise(call: ApplicationCall, portfolio: Portfolio) {
        val req = ChatCompletionRequest(
            model = ModelId("gpt-4o"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.User,
                    content = "Given holdings ${portfolio.holdings} and goal '${portfolio.goal}', " +
                        "recommend specific trades to make this week."
                )
            )
        )
        val advice = client.chatCompletion(req).choices.first().message.content.orEmpty()
        // sink: raw LLM financial advice returned authoritatively (no disclaimer, no human review)
        call.respond(mapOf("recommendation" to advice))
    }
}
