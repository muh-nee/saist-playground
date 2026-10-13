package com.example.ai

import dev.langchain4j.model.chat.ChatLanguageModel
import java.security.MessageDigest
import java.sql.ResultSet
import java.util.Base64

class AccountAssistant(private val model: ChatLanguageModel, private val rs: ResultSet) {

    private fun hash(value: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(value.toByteArray())
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest).take(12)
    }

    fun summarize(): String {
        val email = rs.getString("email")
        val ssn = rs.getString("ssn")
        // safe: PII hashed before entering the prompt
        return model.generate(
            "Summarize activity for user emailHash=${hash(email)} ssnHash=${hash(ssn)}."
        )
    }
}
