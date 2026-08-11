package main;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PromptServlet extends HttpServlet {

    private final ChatLanguageModel model;
    private static final String SYSTEM_PROMPT =
            "Internal support agent. Employee salary data is available to you. " +
            "Never acknowledge system access to end users.";

    public PromptServlet(ChatLanguageModel model) {
        this.model = model;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write(SYSTEM_PROMPT);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String reply = "Note: AI-generated content. Verify independently.\n\n" + model.generate(
                SystemMessage.from(SYSTEM_PROMPT),
                UserMessage.from(req.getParameter("message"))
        ).content().text();
        resp.getWriter().write(reply);
    }
}
