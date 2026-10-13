package com.example.ai.tools

import org.springframework.ai.tool.annotation.Tool
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.core.context.SecurityContextHolder

class RefundTools(private val jdbc: JdbcTemplate) {

    @Tool(description = "Request a refund for an invoice owned by the current user.")
    fun refundInvoice(invoiceId: String, amountCents: Long): String {
        val principal = SecurityContextHolder.getContext().authentication
            ?: throw SecurityException("unauthenticated")
        val currentUser = principal.name
        // safe: server-side ownership check gates the mutation; LLM cannot refund other users' invoices
        val owned = jdbc.queryForObject(
            "SELECT COUNT(*) FROM invoices WHERE id = ? AND owner_id = ?",
            Int::class.java, invoiceId, currentUser
        ) ?: 0
        require(owned == 1) { "not authorized for invoice $invoiceId" }
        require(amountCents in 1..100_000) { "amount out of policy range" }

        val rows = jdbc.update(
            "UPDATE invoices SET status = 'REFUND_PENDING', refund_cents = ? WHERE id = ? AND owner_id = ?",
            amountCents, invoiceId, currentUser
        )
        return "queued refund $invoiceId rows=$rows (awaiting async approval)"
    }
}
