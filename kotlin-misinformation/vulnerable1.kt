package com.example.ai.medical

import dev.langchain4j.model.chat.ChatLanguageModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class SymptomReport(val symptoms: String, val patientAge: Int)

@RestController
class TriageController(private val model: ChatLanguageModel) {

    @PostMapping("/triage")
    fun triage(@RequestBody report: SymptomReport): ResponseEntity<Map<String, String>> {
        val diagnosis = model.generate(
            "Patient is ${report.patientAge}. Symptoms: ${report.symptoms}. " +
                "Give the most likely diagnosis and recommended medication."
        )
        // sink: raw LLM medical diagnosis returned to patient with no disclaimer / clinician gate
        return ResponseEntity.ok(mapOf("diagnosis" to diagnosis))
    }
}
