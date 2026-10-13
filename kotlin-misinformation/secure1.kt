package com.example.ai.medical

import dev.langchain4j.model.chat.ChatLanguageModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

data class SymptomReport(val symptoms: String, val patientAge: Int)

interface ClinicianReviewQueue {
    fun enqueue(reviewId: String, patientAge: Int, symptoms: String, draft: String)
}

@RestController
class TriageController(
    private val model: ChatLanguageModel,
    private val reviewQueue: ClinicianReviewQueue,
) {

    @PostMapping("/triage")
    fun triage(@RequestBody report: SymptomReport): ResponseEntity<Map<String, String>> {
        val draft = model.generate(
            "Patient is ${report.patientAge}. Symptoms: ${report.symptoms}. " +
                "Draft a differential for a clinician to review."
        )
        val reviewId = UUID.randomUUID().toString()
        // safe: draft never shown to the patient; routed to a clinician review queue first
        reviewQueue.enqueue(reviewId, report.patientAge, report.symptoms, draft)
        return ResponseEntity.accepted().body(
            mapOf("status" to "pending_clinician_review", "reviewId" to reviewId)
        )
    }
}
