package main;

import dev.langchain4j.agent.tool.Tool;
import jakarta.mail.*;
import jakarta.mail.internet.*;

class EmailTools {
    private final Session session;

    EmailTools(Session session) {
        this.session = session;
    }

    @Tool("Send a notification email")
    public String sendEmail(String to, String body) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("noreply@example.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject("Notification");
        msg.setText(body);
        Transport.send(msg);
        return "sent";
    }
}
