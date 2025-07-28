package com.example.xss;

import org.apache.commons.text.StringEscapeUtils;
import org.owasp.encoder.Encode;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Path("/jaxrs")
public class secure6 {
    
    @GET
    @Path("/user")
    @Produces(MediaType.TEXT_HTML)
    public Response getUserProfile(
            @QueryParam("name") 
            @Size(max = 50, message = "Name must be less than 50 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Name contains invalid characters")
            String userName) {
        
        if (userName == null || !isValidUserName(userName)) {
            String errorHtml = buildSecureErrorResponse("Invalid username provided");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorHtml)
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .build();
        }
        
        String sanitizedName = Encode.forHtml(userName);
        
        StringBuilder html = new StringBuilder();
        html.append("&lt;!DOCTYPE html&gt;");
        html.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;&lt;/head&gt;&lt;body&gt;");
        html.append("&lt;h2&gt;User Profile&lt;/h2&gt;");
        html.append("&lt;p&gt;Name: ").append(sanitizedName).append("&lt;/p&gt;");
        html.append("&lt;/body&gt;&lt;/html&gt;");
        
        return Response.ok(html.toString())
                .header("Content-Type", "text/html; charset=UTF-8")
                .header("X-Content-Type-Options", "nosniff")
                .header("X-Frame-Options", "DENY")
                .header("X-XSS-Protection", "1; mode=block")
                .header("Content-Security-Policy", "default-src 'self'; script-src 'self'")
                .build();
    }
    
    @GET
    @Path("/search")
    @Produces(MediaType.TEXT_HTML)
    public Response searchResults(
            @QueryParam("q") 
            @Size(max = 100, message = "Query must be less than 100 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$", message = "Query contains invalid characters")
            String query,
            @QueryParam("category") 
            @Pattern(regexp = "^(all|docs|code|images)$", message = "Invalid category")
            String category) {
        
        if (query == null || category == null || 
            !isValidSearchQuery(query) || !isValidCategory(category)) {
            
            String errorHtml = buildSecureErrorResponse("Invalid search parameters");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorHtml)
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .build();
        }
        
        String sanitizedQuery = StringEscapeUtils.escapeHtml4(query);
        String sanitizedCategory = StringEscapeUtils.escapeHtml4(category);
        
        StringBuilder resultsHtml = new StringBuilder();
        resultsHtml.append("&lt;div class='search-results'&gt;");
        resultsHtml.append("&lt;h3&gt;Search results for '").append(sanitizedQuery)
                  .append("' in ").append(sanitizedCategory).append("&lt;/h3&gt;");
        resultsHtml.append("&lt;p&gt;No results found for: ").append(sanitizedQuery).append("&lt;/p&gt;");
        resultsHtml.append("&lt;/div&gt;");
        
        return Response.ok(resultsHtml.toString())
                .header("Content-Type", "text/html; charset=UTF-8")
                .header("X-Content-Type-Options", "nosniff")
                .header("X-Frame-Options", "DENY")
                .header("X-XSS-Protection", "1; mode=block")
                .header("Content-Security-Policy", "default-src 'self'; script-src 'self'")
                .build();
    }
    
    @GET
    @Path("/redirect")
    @Produces(MediaType.TEXT_HTML)
    public Response redirectPage(
            @QueryParam("url") 
            @Size(max = 200, message = "URL must be less than 200 characters")
            String redirectUrl,
            @QueryParam("message") 
            @Size(max = 100, message = "Message must be less than 100 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\.,!?\\-]+$", message = "Message contains invalid characters")
            String message) {
        
        if (message == null || !isValidMessage(message)) {
            String errorHtml = buildSecureErrorResponse("Invalid message parameter");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorHtml)
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .build();
        }
        
        if (redirectUrl == null || !isValidRedirectUrl(redirectUrl)) {
            String errorHtml = buildSecureErrorResponse("Invalid or unsafe redirect URL");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorHtml)
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .build();
        }
        
        String sanitizedMessage = StringEscapeUtils.escapeHtml4(message);
        String sanitizedUrl = Encode.forHtml(redirectUrl);
        
        StringBuilder html = new StringBuilder();
        html.append("&lt;!DOCTYPE html&gt;");
        html.append("&lt;html&gt;&lt;head&gt;&lt;meta charset='UTF-8'&gt;&lt;/head&gt;&lt;body&gt;");
        html.append("&lt;p&gt;").append(sanitizedMessage).append("&lt;/p&gt;");
        html.append("&lt;p&gt;Redirecting to: &lt;a href='").append(sanitizedUrl)
                  .append("'&gt;").append(sanitizedUrl).append("&lt;/a&gt;&lt;/p&gt;");
        html.append("&lt;noscript&gt;&lt;p&gt;Please click the link above to continue.&lt;/p&gt;&lt;/noscript&gt;");
        html.append("&lt;/body&gt;&lt;/html&gt;");
        
        return Response.ok(html.toString())
                .header("Content-Type", "text/html; charset=UTF-8")
                .header("X-Content-Type-Options", "nosniff")
                .header("X-Frame-Options", "DENY")
                .header("X-XSS-Protection", "1; mode=block")
                .header("Content-Security-Policy", "default-src 'self'; script-src 'self'")
                .build();
    }
    
    private boolean isValidUserName(String userName) {
        if (userName == null || userName.trim().isEmpty() || userName.length() &gt; 50) {
            return false;
        }
        return userName.matches("^[a-zA-Z0-9\\s]+$");
    }
    
    private boolean isValidSearchQuery(String query) {
        if (query == null || query.trim().isEmpty() || query.length() &gt; 100) {
            return false;
        }
        
        String[] forbiddenPatterns = {"&lt;script", "javascript:", "vbscript:", "data:", "onload", "onerror"};
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
        String[] validCategories = {"all", "docs", "code", "images"};
        for (String valid : validCategories) {
            if (valid.equals(category)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isValidMessage(String message) {
        if (message == null || message.trim().isEmpty() || message.length() &gt; 100) {
            return false;
        }
        return message.matches("^[a-zA-Z0-9\\s\\.,!?\\-]+$");
    }
    
    private boolean isValidRedirectUrl(String url) {
        if (url == null || url.trim().isEmpty() || url.length() &gt; 200) {
            return false;
        }
        
        String[] allowedHosts = {"example.com", "trusted-site.com", "localhost"};
        String lowerUrl = url.toLowerCase();
        
        if (!lowerUrl.startsWith("https://") && !lowerUrl.startsWith("http://")) {
            return false;
        }
        
        for (String host : allowedHosts) {
            if (lowerUrl.contains("://" + host) || lowerUrl.contains("://www." + host)) {
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
}