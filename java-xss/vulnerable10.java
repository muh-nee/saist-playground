package com.example.xss;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/template")
public class vulnerable10 extends HttpServlet {
    
    private Configuration freemarkerConfig;
    
    @Override
    public void init() throws ServletException {
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_31);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userName = request.getParameter("user");
        String welcomeMessage = request.getParameter("message");
        String templateContent = request.getParameter("content");
        
        response.setContentType("text/html");
        
        String htmlOutput = "&lt;html&gt;&lt;body&gt;";
        htmlOutput += "&lt;h1&gt;Welcome " + userName + "!&lt;/h1&gt;";
        htmlOutput += "&lt;div class='message'&gt;" + welcomeMessage + "&lt;/div&gt;";
        
        if (templateContent != null) {
            htmlOutput += "&lt;div class='dynamic-content'&gt;" + templateContent + "&lt;/div&gt;";
        }
        
        htmlOutput += "&lt;/body&gt;&lt;/html&gt;";
        
        response.getWriter().write(htmlOutput);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String dynamicTemplate = request.getParameter("template");
        String userInput = request.getParameter("userInput");
        
        Map&lt;String, Object&gt; dataModel = new HashMap&lt;&gt;();
        dataModel.put("userInput", userInput);
        dataModel.put("rawUserInput", userInput);
        
        String processedTemplate = dynamicTemplate.replace("${userInput}", userInput);
        
        response.setContentType("text/html");
        response.getWriter().write("&lt;html&gt;&lt;body&gt;" + processedTemplate + "&lt;/body&gt;&lt;/html&gt;");
    }
}