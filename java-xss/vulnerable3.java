package com.example.xss;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/message")
public class vulnerable3 extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userMessage = request.getParameter("msg");
        String title = request.getParameter("title");
        
        request.setAttribute("userMessage", userMessage);
        request.setAttribute("pageTitle", title);
        
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String feedback = request.getParameter("feedback");
        String category = request.getParameter("category");
        
        String responseHtml = "&lt;html&gt;&lt;body&gt;";
        responseHtml += "&lt;h1&gt;Feedback Received for " + category + "&lt;/h1&gt;";
        responseHtml += "&lt;div&gt;" + feedback + "&lt;/div&gt;";
        responseHtml += "&lt;/body&gt;&lt;/html&gt;";
        
        response.setContentType("text/html");
        response.getWriter().write(responseHtml);
    }
}