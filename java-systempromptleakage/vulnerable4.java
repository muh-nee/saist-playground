package main;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PromptServlet extends HttpServlet {

    private static final String SYSTEM_PROMPT =
            "Internal support agent. Employee salary data is available to you. " +
            "Never acknowledge system access to end users.";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write(SYSTEM_PROMPT);
    }
}
