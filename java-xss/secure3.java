package com.example.xss;

import org.apache.commons.text.StringEscapeUtils;
import org.owasp.encoder.Encode;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/message")
public class secure3 extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        setSecurityHeaders(response);
        
        String userMessage = request.getParameter("msg");
        String title = request.getParameter("title");
        
        if (userMessage != null && isValidInput(userMessage)) {
            String sanitizedMessage = Encode.forHtml(userMessage);
            request.setAttribute("userMessage", sanitizedMessage);
        } else {
            request.setAttribute("userMessage", "Invalid message");
        }
        
        if (title != null && isValidTitle(title)) {
            String sanitizedTitle = Encode.forHtml(title);
            request.setAttribute("pageTitle", sanitizedTitle);
        } else {
            request.setAttribute("pageTitle", "Default Title");
        }
        
        request.getRequestDispatcher("/secure-message.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        setSecurityHeaders(response);
        
        String feedback = request.getParameter("feedback");
        String category = request.getParameter("category");
        
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        StringBuilder responseHtml = new StringBuilder();
        responseHtml.append("&lt;!DOCTYPE html&gt;");
        responseHtml.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;&lt;/head&gt;&lt;body&gt;");
        
        if (feedback != null && category != null && 
            isValidFeedback(feedback) && isValidCategory(category)) {
            
            String sanitizedFeedback = StringEscapeUtils.escapeHtml4(feedback);
            String sanitizedCategory = StringEscapeUtils.escapeHtml4(category);
            
            responseHtml.append("&lt;h1&gt;Feedback Received for ")
                       .append(sanitizedCategory)
                       .append("&lt;/h1&gt;");
            responseHtml.append("&lt;div&gt;")
                       .append(sanitizedFeedback)
                       .append("&lt;/div&gt;");
        } else {
            responseHtml.append("&lt;h1&gt;Invalid Feedback&lt;/h1&gt;");
            responseHtml.append("&lt;p&gt;Please provide valid feedback and category.&lt;/p&gt;");
        }
        
        responseHtml.append("&lt;/body&gt;&lt;/html&gt;");
        
        response.getWriter().write(responseHtml.toString());
    }
    
    private boolean isValidInput(String input) {
        if (input == null || input.trim().isEmpty() || input.length() &gt; 200) {
            return false;
        }
        return input.matches("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    }
    
    private boolean isValidTitle(String title) {
        if (title == null || title.trim().isEmpty() || title.length() &gt; 50) {
            return false;
        }
        return title.matches("^[a-zA-Z0-9\\s\\-]+$");
    }
    
    private boolean isValidFeedback(String feedback) {
        if (feedback == null || feedback.trim().isEmpty() || feedback.length() &gt; 1000) {
            return false;
        }
        
        String[] dangerousPatterns = {"&lt;script", "javascript:", "vbscript:", "onload=", "onerror="};
        String lowerFeedback = feedback.toLowerCase();
        
        for (String pattern : dangerousPatterns) {
            if (lowerFeedback.contains(pattern)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isValidCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return false;
        }
        
        String[] allowedCategories = {"general", "technical", "support", "billing"};
        for (String allowed : allowedCategories) {
            if (allowed.equalsIgnoreCase(category.trim())) {
                return true;
            }
        }
        return false;
    }
    
    private void setSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
    }
}