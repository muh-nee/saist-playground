package com.example.xss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootApplication
@RestController
@Validated
public class secure4 {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) {
        SpringApplication.run(secure4.class, args);
    }
    
    @GetMapping("/api/user")
    public ResponseEntity&lt;String&gt; getUserInfo(
            @RequestParam("name") 
            @Size(max = 50, message = "Name must be less than 50 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Name contains invalid characters")
            String userName) {
        
        try {
            ObjectNode jsonResponse = objectMapper.createObjectNode();
            
            if (isValidUserName(userName)) {
                String sanitizedName = HtmlUtils.htmlEscape(userName);
                jsonResponse.put("message", "Hello " + sanitizedName);
                jsonResponse.put("status", "success");
            } else {
                jsonResponse.put("message", "Invalid username provided");
                jsonResponse.put("status", "error");
            }
            
            HttpHeaders headers = createSecurityHeaders();
            headers.add("Content-Type", "application/json");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(jsonResponse.toString());
                    
        } catch (Exception e) {
            ObjectNode errorResponse = objectMapper.createObjectNode();
            errorResponse.put("message", "An error occurred");
            errorResponse.put("status", "error");
            
            return ResponseEntity.badRequest()
                    .headers(createSecurityHeaders())
                    .body(errorResponse.toString());
        }
    }
    
    @GetMapping("/api/error")
    public ResponseEntity&lt;String&gt; getErrorPage(
            @RequestParam("error") 
            @Size(max = 100, message = "Error message too long")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\.,!?\\-]+$", message = "Error message contains invalid characters")
            String errorMsg) {
        
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("&lt;!DOCTYPE html&gt;");
        htmlResponse.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;&lt;/head&gt;&lt;body&gt;");
        htmlResponse.append("&lt;h1&gt;Error Occurred&lt;/h1&gt;");
        
        if (errorMsg != null && isValidErrorMessage(errorMsg)) {
            String sanitizedError = HtmlUtils.htmlEscape(errorMsg);
            htmlResponse.append("&lt;p&gt;Error details: ").append(sanitizedError).append("&lt;/p&gt;");
        } else {
            htmlResponse.append("&lt;p&gt;An unknown error occurred.&lt;/p&gt;");
        }
        
        htmlResponse.append("&lt;/body&gt;&lt;/html&gt;");
        
        HttpHeaders headers = createSecurityHeaders();
        headers.add("Content-Type", "text/html; charset=UTF-8");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(htmlResponse.toString());
    }
    
    @GetMapping("/api/notification")
    public ResponseEntity&lt;String&gt; showNotification(
            @RequestParam("msg") 
            @Size(max = 200, message = "Message too long")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\.,!?\\-]+$", message = "Message contains invalid characters")
            String message,
            @RequestParam("type")
            @Pattern(regexp = "^(info|warning|error|success)$", message = "Invalid alert type")
            String alertType) {
        
        if (message == null || alertType == null || 
            !isValidMessage(message) || !isValidAlertType(alertType)) {
            
            HttpHeaders headers = createSecurityHeaders();
            headers.add("Content-Type", "text/html; charset=UTF-8");
            
            return ResponseEntity.badRequest()
                    .headers(headers)
                    .body("&lt;div class='alert alert-error'&gt;Invalid notification parameters&lt;/div&gt;");
        }
        
        String sanitizedMessage = HtmlUtils.htmlEscape(message);
        String sanitizedAlertType = HtmlUtils.htmlEscape(alertType);
        
        String notificationHtml = "&lt;div class='alert alert-" + sanitizedAlertType + "'&gt;" + 
                                 sanitizedMessage + "&lt;/div&gt;";
        
        HttpHeaders headers = createSecurityHeaders();
        headers.add("Content-Type", "text/html; charset=UTF-8");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(notificationHtml);
    }
    
    private boolean isValidUserName(String userName) {
        if (userName == null || userName.trim().isEmpty() || userName.length() &gt; 50) {
            return false;
        }
        return userName.matches("^[a-zA-Z0-9\\s]+$");
    }
    
    private boolean isValidErrorMessage(String errorMsg) {
        if (errorMsg == null || errorMsg.trim().isEmpty() || errorMsg.length() &gt; 100) {
            return false;
        }
        
        String[] forbiddenPatterns = {"&lt;script", "javascript:", "vbscript:", "onload", "onerror"};
        String lowerError = errorMsg.toLowerCase();
        
        for (String pattern : forbiddenPatterns) {
            if (lowerError.contains(pattern)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isValidMessage(String message) {
        if (message == null || message.trim().isEmpty() || message.length() &gt; 200) {
            return false;
        }
        return message.matches("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    }
    
    private boolean isValidAlertType(String alertType) {
        String[] validTypes = {"info", "warning", "error", "success"};
        for (String validType : validTypes) {
            if (validType.equals(alertType)) {
                return true;
            }
        }
        return false;
    }
    
    private HttpHeaders createSecurityHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Content-Type-Options", "nosniff");
        headers.add("X-Frame-Options", "DENY");
        headers.add("X-XSS-Protection", "1; mode=block");
        headers.add("Content-Security-Policy", "default-src 'self'; script-src 'self'");
        headers.add("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        return headers;
    }
}