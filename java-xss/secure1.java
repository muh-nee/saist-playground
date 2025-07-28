package com.example.xss;

import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class secure1 extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'");
        
        PrintWriter out = response.getWriter();
        
        String searchQuery = request.getParameter("q");
        
        out.println("&lt;html&gt;");
        out.println("&lt;head&gt;&lt;title&gt;Search Results&lt;/title&gt;&lt;/head&gt;");
        out.println("&lt;body&gt;");
        out.println("&lt;h1&gt;Search Results&lt;/h1&gt;");
        
        if (searchQuery != null) {
            String sanitizedQuery = StringEscapeUtils.escapeHtml4(searchQuery);
            
            if (isValidSearchQuery(searchQuery)) {
                out.println("&lt;p&gt;You searched for: " + sanitizedQuery + "&lt;/p&gt;");
            } else {
                out.println("&lt;p&gt;Invalid search query. Please try again.&lt;/p&gt;");
            }
        }
        
        out.println("&lt;/body&gt;");
        out.println("&lt;/html&gt;");
    }
    
    private boolean isValidSearchQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            return false;
        }
        
        if (query.length() &gt; 100) {
            return false;
        }
        
        return query.matches("^[a-zA-Z0-9\\s\\-_]+$");
    }
}