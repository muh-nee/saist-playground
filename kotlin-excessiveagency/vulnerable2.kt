package com.example.ai.tools

import dev.langchain4j.agent.tool.Tool
import jakarta.mail.Message
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.Properties

class MailTools(private val session: Session = Session.getInstance(Properties())) {

    @Tool("Send an email to any recipient with any body.")
    fun sendMail(recipient: String, subject: String, body: String): String {
        // sink: LLM-controlled recipient + body sent via SMTP with no auth/allowlist
        val msg: Message = MimeMessage(session).apply {
            setFrom(InternetAddress("noreply@example.com"))
            setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
            setSubject(subject)
            setText(body)
        }
        Transport.send(msg)
        return "sent to $recipient"
    }
}
