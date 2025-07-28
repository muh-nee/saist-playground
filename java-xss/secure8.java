package com.example.xss;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class secure8 extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String userName;
    private String userMessage;
    private String searchQuery;
    private String sanitizedContent;
    private boolean isInputValid;
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    public String displayUser() {
        setSecurityHeaders();
        
        String rawUserName = request.getParameter("user");
        
        if (rawUserName == null || !isValidUserName(rawUserName)) {
            sanitizedContent = "&lt;div class='error'&gt;Invalid username provided&lt;/div&gt;";
            isInputValid = false;
            return ERROR;
        }
        
        userName = StringEscapeUtils.escapeHtml4(rawUserName);
        
        StringBuilder content = new StringBuilder();
        content.append("&lt;div class='user-info'&gt;");
        content.append("&lt;h2&gt;Welcome ").append(userName).append("!&lt;/h2&gt;");
        content.append("&lt;p&gt;User: ").append(userName).append("&lt;/p&gt;");
        content.append("&lt;/div&gt;");
        
        sanitizedContent = content.toString();
        isInputValid = true;
        
        return SUCCESS;
    }
    
    public String processMessage() {
        setSecurityHeaders();
        
        String rawMessage = request.getParameter("message");
        String messageType = request.getParameter("type");
        
        if (rawMessage == null || messageType == null || 
            !isValidMessage(rawMessage) || !isValidMessageType(messageType)) {
            
            sanitizedContent = "&lt;div class='error'&gt;Invalid message parameters&lt;/div&gt;";
            isInputValid = false;
            return ERROR;
        }
        
        userMessage = StringEscapeUtils.escapeHtml4(rawMessage);
        String sanitizedType = StringEscapeUtils.escapeHtml4(messageType);
        
        StringBuilder content = new StringBuilder();
        content.append("&lt;div class='message ").append(sanitizedType).append("'&gt;");
        content.append(userMessage);
        content.append("&lt;/div&gt;");
        
        sanitizedContent = content.toString();
        isInputValid = true;
        
        return SUCCESS;
    }
    
    public String searchResults() {
        setSecurityHeaders();
        
        String rawQuery = request.getParameter("q");
        String category = request.getParameter("category");
        
        if (rawQuery == null || category == null || 
            !isValidSearchQuery(rawQuery) || !isValidCategory(category)) {
            
            sanitizedContent = "&lt;div class='error'&gt;Invalid search parameters&lt;/div&gt;";
            isInputValid = false;
            return ERROR;
        }
        
        searchQuery = StringEscapeUtils.escapeHtml4(rawQuery);
        String sanitizedCategory = StringEscapeUtils.escapeHtml4(category);
        
        StringBuilder content = new StringBuilder();
        content.append("&lt;h1&gt;Search Results&lt;/h1&gt;");
        content.append("&lt;p&gt;You searched for: ").append(searchQuery)
               .append(" in ").append(sanitizedCategory).append("&lt;/p&gt;");
        content.append("&lt;div&gt;Results for '").append(searchQuery).append("'&lt;/div&gt;");
        
        sanitizedContent = content.toString();
        isInputValid = true;
        
        return SUCCESS;
    }
    
    private boolean isValidUserName(String userName) {
        if (userName == null || userName.trim().isEmpty() || userName.length() &gt; 50) {
            return false;
        }
        
        String[] forbiddenPatterns = {"&lt;script", "javascript:", "vbscript:", "onload", "onerror", "eval("};
        String lowerUserName = userName.toLowerCase();
        
        for (String pattern : forbiddenPatterns) {
            if (lowerUserName.contains(pattern)) {
                return false;
            }
        }
        
        return userName.matches("^[a-zA-Z0-9\\s]+$");
    }
    
    private boolean isValidMessage(String message) {
        if (message == null || message.trim().isEmpty() || message.length() &gt; 500) {
            return false;
        }
        
        String[] dangerousKeywords = {"script", "javascript", "vbscript", "onload", "onerror", "iframe", "object", "embed"};
        String lowerMessage = message.toLowerCase();
        
        for (String keyword : dangerousKeywords) {
            if (lowerMessage.contains(keyword)) {
                return false;
            }
        }
        
        return message.matches("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    }
    
    private boolean isValidMessageType(String type) {
        if (type == null) {
            return false;
        }
        String[] validTypes = {"info", "warning", "error", "success"};
        for (String validType : validTypes) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isValidSearchQuery(String query) {
        if (query == null || query.trim().isEmpty() || query.length() &gt; 100) {
            return false;
        }
        
        String[] forbiddenPatterns = {"&lt;", "&gt;", "&amp;lt;script", "javascript:", "vbscript:", "data:", "onload", "onerror"};
        String lowerQuery = query.toLowerCase();
        
        for (String pattern : forbiddenPatterns) {
            if (lowerQuery.contains(pattern)) {
                return false;
            }
        }
        
        return query.matches("^[a-zA-Z0-9\\s\\-]+$");
    }
    
    private boolean isValidCategory(String category) {
        if (category == null) {
            return false;
        }
        String[] validCategories = {"all", "products", "services", "support", "documentation"};
        for (String validCategory : validCategories) {
            if (validCategory.equals(category)) {
                return true;
            }
        }
        return false;
    }
    
    private void setSecurityHeaders() {
        if (response != null) {
            response.setHeader("X-Content-Type-Options", "nosniff");
            response.setHeader("X-Frame-Options", "DENY");
            response.setHeader("X-XSS-Protection", "1; mode=block");
            response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'");
            response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
            response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        }
    }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }
    
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    
    public String getSanitizedContent() { return sanitizedContent; }
    public void setSanitizedContent(String sanitizedContent) { this.sanitizedContent = sanitizedContent; }
    
    public boolean getIsInputValid() { return isInputValid; }
    public void setIsInputValid(boolean isInputValid) { this.isInputValid = isInputValid; }
}