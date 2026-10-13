package com.example.ai

import dev.langchain4j.model.chat.ChatLanguageModel
import java.sql.ResultSet

class AccountAssistant(private val model: ChatLanguageModel, private val rs: ResultSet) {

    fun summarize(): String {
        val email = rs.getString("email")
        val ssn = rs.getString("ssn")
        val creditCard = rs.getString("credit_card")
        // sink: PII flows directly into LLM prompt content
        return model.generate(
            "Summarize activity for customer $email (SSN: $ssn, card: $creditCard)."
        )
    }
}
