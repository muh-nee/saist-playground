package com.example.xss;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Pattern;

public class secure9 extends Controller {
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s]+$");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    private static final Pattern SEARCH_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s\\-]+$");
    private static final Pattern ERROR_CODE_PATTERN = Pattern.compile("^[0-9]{3}$");
    
    public Result userProfile(Http.Request request) {
        String username = request.getQueryString("name");
        String bio = request.getQueryString("bio");
        
        if (username == null || bio == null || 
            !isValidUsername(username) || !isValidBio(bio)) {
            
            String errorHtml = buildSecureErrorPage("Invalid profile parameters");
            return badRequest(errorHtml).as("text/html; charset=UTF-8");
        }
        
        String sanitizedUsername = StringEscapeUtils.escapeHtml4(username);
        String sanitizedBio = StringEscapeUtils.escapeHtml4(bio);
        
        StringBuilder html = new StringBuilder();
        html.append("&lt;!DOCTYPE html&gt;");
        html.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;&lt;/head&gt;&lt;body&gt;");
        html.append("&lt;h1&gt;Profile for ").append(sanitizedUsername).append("&lt;/h1&gt;");
        html.append("&lt;div class='bio'&gt;").append(sanitizedBio).append("&lt;/div&gt;");
        html.append("&lt;/body&gt;&lt;/html&gt;");
        
        return ok(html.toString())
                .as("text/html; charset=UTF-8")
                .withHeader("X-Content-Type-Options", "nosniff")
                .withHeader("X-Frame-Options", "DENY")
                .withHeader("X-XSS-Protection", "1; mode=block")
                .withHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'")
                .withHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
    }
    
    public Result displayComment(Http.Request request) {
        String comment = request.getQueryString("comment");
        String author = request.getQueryString("author");
        
        if (comment == null || author == null || 
            !isValidComment(comment) || !isValidAuthor(author)) {
            
            String errorHtml = buildSecureErrorPage("Invalid comment parameters");
            return badRequest(errorHtml).as("text/html; charset=UTF-8");
        }
        
        String sanitizedComment = StringEscapeUtils.escapeHtml4(comment);
        String sanitizedAuthor = StringEscapeUtils.escapeHtml4(author);
        
        StringBuilder commentHtml = new StringBuilder();
        commentHtml.append("&lt;div class='comment-box'&gt;");
        commentHtml.append("&lt;h3&gt;Comment by ").append(sanitizedAuthor).append("&lt;/h3&gt;");
        commentHtml.append("&lt;p&gt;").append(sanitizedComment).append("&lt;/p&gt;");
        commentHtml.append("&lt;/div&gt;");
        
        return ok(commentHtml.toString())
                .as("text/html; charset=UTF-8")
                .withHeader("X-Content-Type-Options", "nosniff")
                .withHeader("X-Frame-Options", "DENY")
                .withHeader("X-XSS-Protection", "1; mode=block")
                .withHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'");
    }
    
    public Result errorPage(Http.Request request) {
        String errorMsg = request.getQueryString("error");
        String errorCode = request.getQueryString("code");
        
        if (errorMsg == null || errorCode == null || 
            !isValidErrorMessage(errorMsg) || !isValidErrorCode(errorCode)) {
            
            String errorHtml = buildSecureErrorPage("Invalid error parameters");
            return badRequest(errorHtml).as("text/html; charset=UTF-8");
        }
        
        String sanitizedErrorMsg = StringEscapeUtils.escapeHtml4(errorMsg);
        String sanitizedErrorCode = StringEscapeUtils.escapeHtml4(errorCode);
        
        StringBuilder errorHtml = new StringBuilder();
        errorHtml.append("&lt;!DOCTYPE html&gt;");
        errorHtml.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;");
        errorHtml.append("&lt;title&gt;Error ").append(sanitizedErrorCode).append("&lt;/title&gt;&lt;/head&gt;");
        errorHtml.append("&lt;body&gt;&lt;h1&gt;Error ").append(sanitizedErrorCode).append("&lt;/h1&gt;");
        errorHtml.append("&lt;p&gt;Error message: ").append(sanitizedErrorMsg).append("&lt;/p&gt;");
        errorHtml.append("&lt;/body&gt;&lt;/html&gt;");
        
        return internalServerError(errorHtml.toString())
                .as("text/html; charset=UTF-8")
                .withHeader("X-Content-Type-Options", "nosniff")
                .withHeader("X-Frame-Options", "DENY")
                .withHeader("X-XSS-Protection", "1; mode=block")
                .withHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'");
    }
    
    public Result searchResults(Http.Request request) {
        String query = request.getQueryString("q");
        String filter = request.getQueryString("filter");
        
        if (query == null || filter == null || 
            !isValidSearchQuery(query) || !isValidFilter(filter)) {
            
            String errorHtml = buildSecureErrorPage("Invalid search parameters");
            return badRequest(errorHtml).as("text/html; charset=UTF-8");
        }
        
        String sanitizedQuery = StringEscapeUtils.escapeHtml4(query);
        String sanitizedFilter = StringEscapeUtils.escapeHtml4(filter);
        
        StringBuilder resultsHtml = new StringBuilder();
        resultsHtml.append("&lt;div class='search-container'&gt;");
        resultsHtml.append("&lt;h2&gt;Search Results&lt;/h2&gt;");
        resultsHtml.append("&lt;p&gt;Query: ").append(sanitizedQuery)
                  .append(" (Filter: ").append(sanitizedFilter).append(")&lt;/p&gt;");
        resultsHtml.append("&lt;div class='results'&gt;No results found for: ")
                  .append(sanitizedQuery).append("&lt;/div&gt;");
        resultsHtml.append("&lt;/div&gt;");
        
        return ok(resultsHtml.toString())
                .as("text/html; charset=UTF-8")
                .withHeader("X-Content-Type-Options", "nosniff")
                .withHeader("X-Frame-Options", "DENY")
                .withHeader("X-XSS-Protection", "1; mode=block")
                .withHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'");
    }
    
    private boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.length() &gt; 50) {
            return false;
        }
        
        String[] forbiddenKeywords = {"script", "javascript", "vbscript", "onload", "onerror"};
        String lowerUsername = username.toLowerCase();
        
        for (String keyword : forbiddenKeywords) {
            if (lowerUsername.contains(keyword)) {
                return false;
            }
        }
        
        return USERNAME_PATTERN.matcher(username).matches();
    }
    
    private boolean isValidBio(String bio) {
        if (bio == null || bio.trim().isEmpty() || bio.length() &gt; 500) {
            return false;
        }
        
        String[] dangerousPatterns = {"&lt;script", "javascript:", "vbscript:", "&lt;iframe", "onload=", "onerror="};
        String lowerBio = bio.toLowerCase();
        
        for (String pattern : dangerousPatterns) {
            if (lowerBio.contains(pattern)) {
                return false;
            }
        }
        
        return bio.matches("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    }
    
    private boolean isValidComment(String comment) {
        if (comment == null || comment.trim().isEmpty() || comment.length() &gt; 300) {
            return false;
        }
        return COMMENT_PATTERN.matcher(comment).matches();
    }
    
    private boolean isValidAuthor(String author) {
        if (author == null || author.trim().isEmpty() || author.length() &gt; 50) {
            return false;
        }
        return USERNAME_PATTERN.matcher(author).matches();
    }
    
    private boolean isValidErrorMessage(String errorMsg) {
        if (errorMsg == null || errorMsg.trim().isEmpty() || errorMsg.length() &gt; 100) {
            return false;
        }
        return errorMsg.matches("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    }
    
    private boolean isValidErrorCode(String errorCode) {
        if (errorCode == null) {
            return false;
        }
        return ERROR_CODE_PATTERN.matcher(errorCode).matches();
    }
    
    private boolean isValidSearchQuery(String query) {
        if (query == null || query.trim().isEmpty() || query.length() &gt; 100) {
            return false;
        }
        return SEARCH_PATTERN.matcher(query).matches();
    }
    
    private boolean isValidFilter(String filter) {
        if (filter == null) {
            return false;
        }
        String[] validFilters = {"all", "recent", "popular", "archived"};
        for (String validFilter : validFilters) {
            if (validFilter.equals(filter)) {
                return true;
            }
        }
        return false;
    }
    
    private String buildSecureErrorPage(String errorMessage) {
        String sanitizedError = StringEscapeUtils.escapeHtml4(errorMessage);
        
        StringBuilder html = new StringBuilder();
        html.append("&lt;!DOCTYPE html&gt;");
        html.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;&lt;/head&gt;&lt;body&gt;");
        html.append("&lt;h1&gt;Error&lt;/h1&gt;");
        html.append("&lt;p&gt;").append(sanitizedError).append("&lt;/p&gt;");
        html.append("&lt;/body&gt;&lt;/html&gt;");
        
        return html.toString();
    }
}