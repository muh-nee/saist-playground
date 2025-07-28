package com.example.xss;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class vulnerable1 extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String searchQuery = request.getParameter("q");
        
        out.println("&lt;html&gt;");
        out.println("&lt;head&gt;&lt;title&gt;Search Results&lt;/title&gt;&lt;/head&gt;");
        out.println("&lt;body&gt;");
        out.println("&lt;h1&gt;Search Results&lt;/h1&gt;");
        
        if (searchQuery != null) {
            out.println("&lt;p&gt;You searched for: " + searchQuery + "&lt;/p&gt;");
        }
        
        out.println("&lt;/body&gt;");
        out.println("&lt;/html&gt;");
    }
}