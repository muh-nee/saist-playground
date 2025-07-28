package com.example.xss;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.validation.Validated;
import org.apache.commons.text.StringEscapeUtils;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Controller("/micronaut")
@Validated
public class secure5 {
    
    @Get("/welcome")
    public HttpResponse&lt;String&gt; welcome(
            @QueryValue("user") 
            @Size(max = 50, message = "Username must be less than 50 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Username contains invalid characters")
            String username) {
        
        if (username == null || !isValidUsername(username)) {
            String errorHtml = buildSecureHtml("Error", "Invalid username provided");
            return HttpResponse.badRequest(errorHtml)
                    .header("Content-Type", "text/html; charset=UTF-8");
        }
        
        String sanitizedUsername = StringEscapeUtils.escapeHtml4(username);
        String html = buildSecureHtml("Welcome", "Welcome to Micronaut, " + sanitizedUsername + "!");
        
        return HttpResponse.ok(html)
                .header("Content-Type", "text/html; charset=UTF-8");
    }
    
    @Get("/status")
    public HttpResponse&lt;String&gt; getStatus(
            @QueryValue("message") 
            @Size(max = 200, message = "Status message must be less than 200 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\.,!?\\-]+$", message = "Status message contains invalid characters")
            String statusMessage) {
        
        if (statusMessage == null || !isValidStatusMessage(statusMessage)) {
            return HttpResponse.badRequest("&lt;div class='status-box error'&gt;&lt;p&gt;Invalid status message&lt;/p&gt;&lt;/div&gt;")
                    .header("Content-Type", "text/html; charset=UTF-8");
        }
        
        String sanitizedMessage = StringEscapeUtils.escapeHtml4(statusMessage);
        String statusHtml = "&lt;div class='status-box'&gt;&lt;p&gt;" + sanitizedMessage + "&lt;/p&gt;&lt;/div&gt;";
        
        return HttpResponse.ok(statusHtml)
                .header("Content-Type", "text/html; charset=UTF-8");
    }
    
    @Get("/dashboard")
    public HttpResponse&lt;String&gt; dashboard(
            @QueryValue("alert") 
            @Size(max = 100, message = "Alert text must be less than 100 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\.,!?\\-]+$", message = "Alert text contains invalid characters")
            String alertText,
            @QueryValue("title") 
            @Size(max = 50, message = "Title must be less than 50 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$", message = "Title contains invalid characters")
            String pageTitle) {
        
        if (alertText == null || pageTitle == null || 
            !isValidAlertText(alertText) || !isValidTitle(pageTitle)) {
            
            String errorHtml = buildSecureHtml("Error", "Invalid dashboard parameters");
            return HttpResponse.badRequest(errorHtml);
        }
        
        String sanitizedTitle = StringEscapeUtils.escapeHtml4(pageTitle);
        String sanitizedAlert = StringEscapeUtils.escapeHtml4(alertText);
        
        StringBuilder html = new StringBuilder();
        html.append("&lt;!DOCTYPE html&gt;");
        html.append("&lt;html&gt;");
        html.append("&lt;head&gt;");
        html.append("&lt;meta charset='UTF-8'&gt;");
        html.append("&lt;title&gt;").append(sanitizedTitle).append("&lt;/title&gt;");
        html.append("&lt;meta http-equiv='Content-Security-Policy' content=\"default-src 'self'; script-src 'self'\"&gt;");
        html.append("&lt;/head&gt;");
        html.append("&lt;body&gt;");
        html.append("&lt;div class='alert'&gt;").append(sanitizedAlert).append("&lt;/div&gt;");
        html.append("&lt;/body&gt;");
        html.append("&lt;/html&gt;");
        
        return HttpResponse.ok(html.toString())
                .header("Content-Type", "text/html; charset=UTF-8")
                .header("X-Content-Type-Options", "nosniff")
                .header("X-Frame-Options", "DENY")
                .header("X-XSS-Protection", "1; mode=block")
                .header("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
    }
    
    private boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.length() &gt; 50) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9\\s]+$");
    }
    
    private boolean isValidStatusMessage(String message) {
        if (message == null || message.trim().isEmpty() || message.length() &gt; 200) {
            return false;
        }
        
        String[] forbiddenKeywords = {"script", "javascript", "vbscript", "onload", "onerror", "eval"};
        String lowerMessage = message.toLowerCase();
        
        for (String keyword : forbiddenKeywords) {
            if (lowerMessage.contains(keyword)) {
                return false;
            }
        }
        
        return message.matches("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    }
    
    private boolean isValidAlertText(String alertText) {
        if (alertText == null || alertText.trim().isEmpty() || alertText.length() &gt; 100) {
            return false;
        }
        return alertText.matches("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    }
    
    private boolean isValidTitle(String title) {
        if (title == null || title.trim().isEmpty() || title.length() &gt; 50) {
            return false;
        }
        return title.matches("^[a-zA-Z0-9\\s\\-]+$");
    }
    
    private String buildSecureHtml(String title, String content) {
        StringBuilder html = new StringBuilder();
        html.append("&lt;!DOCTYPE html&gt;");
        html.append("&lt;html&gt;");
        html.append("&lt;head&gt;");
        html.append("&lt;meta charset='UTF-8'&gt;");
        html.append("&lt;title&gt;").append(StringEscapeUtils.escapeHtml4(title)).append("&lt;/title&gt;");
        html.append("&lt;meta http-equiv='Content-Security-Policy' content=\"default-src 'self'; script-src 'self'\"&gt;");
        html.append("&lt;/head&gt;");
        html.append("&lt;body&gt;");
        html.append("&lt;h1&gt;").append(StringEscapeUtils.escapeHtml4(content)).append("&lt;/h1&gt;");
        html.append("&lt;/body&gt;");
        html.append("&lt;/html&gt;");
        return html.toString();
    }
}