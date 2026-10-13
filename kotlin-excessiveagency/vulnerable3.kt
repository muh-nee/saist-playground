package com.example.ai.tools

import org.springframework.ai.tool.annotation.Tool
import org.springframework.jdbc.core.JdbcTemplate

class RefundTools(private val jdbc: JdbcTemplate) {

    @Tool(description = "Mark an invoice as refunded by invoice id.")
    fun refundInvoice(invoiceId: String, amountCents: Long): String {
        // sink: LLM-controlled id/amount drives a DB mutation with no auth or ownership check
        val rows = jdbc.update(
            "UPDATE invoices SET status = 'REFUNDED', refund_cents = ? WHERE id = ?",
            amountCents, invoiceId
        )
        return "refunded $invoiceId rows=$rows"
    }
}
// registered via: ChatClient.builder(model).defaultTools(RefundTools(jdbc)).build()
