package com.example.xss;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.text.StringEscapeUtils;
import org.owasp.encoder.Encode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@WebServlet("/template")
public class secure10 extends HttpServlet {
    
    private Configuration freemarkerConfig;
    private static final Pattern USER_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s]+$");
    private static final Pattern MESSAGE_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    
    @Override
    public void init() throws ServletException {
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_31);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freemarkerConfig.setLogTemplateExceptions(false);
        freemarkerConfig.setWrapUncheckedExceptions(true);
        freemarkerConfig.setFallbackOnNullLoopVariable(false);
        
        freemarkerConfig.setAutoEscapingPolicy(Configuration.ENABLE_IF_DEFAULT_AUTO_ESCAPING_POLICY);
        freemarkerConfig.setOutputFormat(freemarker.core.HTMLOutputFormat.INSTANCE);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        setSecurityHeaders(response);
        
        String userName = request.getParameter("user");
        String welcomeMessage = request.getParameter("message");
        String templateContent = request.getParameter("content");
        
        response.setContentType("text/html; charset=UTF-8");
        
        if (userName == null || welcomeMessage == null || 
            !isValidUser(userName) || !isValidMessage(welcomeMessage)) {
            
            String errorHtml = buildSecureErrorResponse("Invalid parameters provided");
            response.getWriter().write(errorHtml);
            return;
        }
        
        String sanitizedUserName = Encode.forHtml(userName);
        String sanitizedMessage = Encode.forHtml(welcomeMessage);
        
        StringBuilder htmlOutput = new StringBuilder();
        htmlOutput.append("&lt;!DOCTYPE html&gt;");
        htmlOutput.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;&lt;/head&gt;&lt;body&gt;");
        htmlOutput.append("&lt;h1&gt;Welcome ").append(sanitizedUserName).append("!&lt;/h1&gt;");
        htmlOutput.append("&lt;div class='message'&gt;").append(sanitizedMessage).append("&lt;/div&gt;");
        
        if (templateContent != null && isValidTemplateContent(templateContent)) {
            String sanitizedContent = StringEscapeUtils.escapeHtml4(templateContent);
            htmlOutput.append("&lt;div class='dynamic-content'&gt;").append(sanitizedContent).append("&lt;/div&gt;");
        }
        
        htmlOutput.append("&lt;/body&gt;&lt;/html&gt;");
        
        response.getWriter().write(htmlOutput.toString());
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        setSecurityHeaders(response);
        
        String userInput = request.getParameter("userInput");
        String templateName = request.getParameter("templateName");
        
        response.setContentType("text/html; charset=UTF-8");
        
        if (userInput == null || templateName == null || 
            !isValidUserInput(userInput) || !isValidTemplateName(templateName)) {
            
            String errorHtml = buildSecureErrorResponse("Invalid template parameters");
            response.getWriter().write(errorHtml);
            return;
        }
        
        try {
            Map&lt;String, Object&gt; dataModel = new HashMap&lt;&gt;();
            
            String sanitizedInput = Encode.forHtml(userInput);
            dataModel.put("userInput", sanitizedInput);
            dataModel.put("displayUserInput", sanitizedInput);
            
            Template template = freemarkerConfig.getTemplate(templateName + ".ftl");
            
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            
            String processedTemplate = stringWriter.toString();
            
            StringBuilder htmlResponse = new StringBuilder();
            htmlResponse.append("&lt;!DOCTYPE html&gt;");
            htmlResponse.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;&lt;/head&gt;&lt;body&gt;");
            htmlResponse.append(processedTemplate);
            htmlResponse.append("&lt;/body&gt;&lt;/html&gt;");
            
            response.getWriter().write(htmlResponse.toString());
            
        } catch (TemplateException e) {
            String errorHtml = buildSecureErrorResponse("Template processing error");
            response.getWriter().write(errorHtml);
        } catch (Exception e) {
            String errorHtml = buildSecureErrorResponse("An unexpected error occurred");
            response.getWriter().write(errorHtml);
        }
    }
    
    private boolean isValidUser(String userName) {
        if (userName == null || userName.trim().isEmpty() || userName.length() &gt; 50) {
            return false;
        }
        
        String[] forbiddenKeywords = {"script", "javascript", "vbscript", "onload", "onerror", "eval"};
        String lowerUserName = userName.toLowerCase();
        
        for (String keyword : forbiddenKeywords) {
            if (lowerUserName.contains(keyword)) {
                return false;
            }
        }
        
        return USER_PATTERN.matcher(userName).matches();
    }
    
    private boolean isValidMessage(String message) {
        if (message == null || message.trim().isEmpty() || message.length() &gt; 200) {
            return false;
        }
        return MESSAGE_PATTERN.matcher(message).matches();
    }
    
    private boolean isValidTemplateContent(String content) {
        if (content == null || content.trim().isEmpty() || content.length() &gt; 1000) {
            return false;
        }
        
        String[] dangerousPatterns = {"&lt;script", "javascript:", "vbscript:", "&lt;iframe", "onload=", "onerror=", "${", "#{", "&lt;#"};
        String lowerContent = content.toLowerCase();
        
        for (String pattern : dangerousPatterns) {
            if (lowerContent.contains(pattern)) {
                return false;
            }
        }
        
        return content.matches("^[a-zA-Z0-9\\s\\.,!?\\-&lt;&gt;/=\"']+$");
    }
    
    private boolean isValidUserInput(String input) {
        if (input == null || input.trim().isEmpty() || input.length() &gt; 300) {
            return false;
        }
        
        String[] forbiddenPatterns = {"&lt;script", "javascript:", "vbscript:", "&lt;iframe", "onload", "onerror", "${", "#{", "&lt;#", "eval("};
        String lowerInput = input.toLowerCase();
        
        for (String pattern : forbiddenPatterns) {
            if (lowerInput.contains(pattern)) {
                return false;
            }
        }
        
        return input.matches("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    }
    
    private boolean isValidTemplateName(String templateName) {
        if (templateName == null || templateName.trim().isEmpty() || templateName.length() &gt; 20) {
            return false;
        }
        
        String[] allowedTemplates = {"welcome", "user-profile", "message-display", "notification"};
        for (String allowedTemplate : allowedTemplates) {
            if (allowedTemplate.equals(templateName)) {
                return true;
            }
        }
        return false;
    }
    
    private String buildSecureErrorResponse(String errorMessage) {
        String sanitizedError = StringEscapeUtils.escapeHtml4(errorMessage);
        
        StringBuilder html = new StringBuilder();
        html.append("&lt;!DOCTYPE html&gt;");
        html.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;&lt;/head&gt;&lt;body&gt;");
        html.append("&lt;h1&gt;Error&lt;/h1&gt;");
        html.append("&lt;p&gt;").append(sanitizedError).append("&lt;/p&gt;");
        html.append("&lt;/body&gt;&lt;/html&gt;");
        
        return html.toString();
    }
    
    private void setSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        response.setHeader("Permissions-Policy", "geolocation=(), microphone=(), camera=()");
    }
}