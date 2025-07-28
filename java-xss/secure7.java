package com.example.xss;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Controller
@Validated
public class secure7 {
    
    @GetMapping("/blog")
    public String showBlogPost(
            @RequestParam("title") 
            @Size(max = 100, message = "Title must be less than 100 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\-.,!?]+$", message = "Title contains invalid characters")
            String postTitle,
            @RequestParam("content") 
            @Size(max = 2000, message = "Content must be less than 2000 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\-.,!?\\n\\r]+$", message = "Content contains invalid characters")
            String postContent,
            Model model,
            HttpServletResponse response) {
        
        setSecurityHeaders(response);
        
        if (postTitle == null || postContent == null || 
            !isValidBlogTitle(postTitle) || !isValidBlogContent(postContent)) {
            
            model.addAttribute("error", "Invalid blog post parameters");
            return "error";
        }
        
        String sanitizedTitle = HtmlUtils.htmlEscape(postTitle);
        String sanitizedContent = HtmlUtils.htmlEscape(postContent);
        
        model.addAttribute("postTitle", sanitizedTitle);
        model.addAttribute("postContent", sanitizedContent);
        model.addAttribute("isContentSafe", true);
        
        return "blog";
    }
    
    @GetMapping("/review")
    public String showReview(
            @RequestParam("reviewer") 
            @Size(max = 50, message = "Reviewer name must be less than 50 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Reviewer name contains invalid characters")
            String reviewerName,
            @RequestParam("rating") 
            @Pattern(regexp = "^[1-5]$", message = "Rating must be between 1 and 5")
            String rating,
            @RequestParam("comment") 
            @Size(max = 500, message = "Comment must be less than 500 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\-.,!?]+$", message = "Comment contains invalid characters")
            String reviewComment,
            Model model,
            HttpServletResponse response) {
        
        setSecurityHeaders(response);
        
        if (reviewerName == null || rating == null || reviewComment == null ||
            !isValidReviewerName(reviewerName) || !isValidRating(rating) || 
            !isValidReviewComment(reviewComment)) {
            
            model.addAttribute("error", "Invalid review parameters");
            return "error";
        }
        
        String sanitizedReviewer = HtmlUtils.htmlEscape(reviewerName);
        String sanitizedRating = HtmlUtils.htmlEscape(rating);
        String sanitizedComment = HtmlUtils.htmlEscape(reviewComment);
        
        model.addAttribute("reviewerName", sanitizedReviewer);
        model.addAttribute("rating", sanitizedRating);
        model.addAttribute("reviewComment", sanitizedComment);
        model.addAttribute("isReviewValid", true);
        
        return "review";
    }
    
    @GetMapping("/announcement")
    public String showAnnouncement(
            @RequestParam("message") 
            @Size(max = 300, message = "Message must be less than 300 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\-.,!?]+$", message = "Message contains invalid characters")
            String announcementMsg,
            @RequestParam("type") 
            @Pattern(regexp = "^(info|warning|error|success)$", message = "Invalid message type")
            String messageType,
            Model model,
            HttpServletResponse response) {
        
        setSecurityHeaders(response);
        
        if (announcementMsg == null || messageType == null ||
            !isValidAnnouncementMessage(announcementMsg) || !isValidMessageType(messageType)) {
            
            model.addAttribute("error", "Invalid announcement parameters");
            return "error";
        }
        
        String sanitizedMessage = HtmlUtils.htmlEscape(announcementMsg);
        String sanitizedType = HtmlUtils.htmlEscape(messageType);
        
        model.addAttribute("announcement", sanitizedMessage);
        model.addAttribute("messageType", sanitizedType);
        model.addAttribute("isAnnouncementSafe", true);
        
        return "announcement";
    }
    
    private boolean isValidBlogTitle(String title) {
        if (title == null || title.trim().isEmpty() || title.length() &gt; 100) {
            return false;
        }
        
        String[] forbiddenKeywords = {"script", "javascript", "vbscript", "onload", "onerror", "iframe"};
        String lowerTitle = title.toLowerCase();
        
        for (String keyword : forbiddenKeywords) {
            if (lowerTitle.contains(keyword)) {
                return false;
            }
        }
        
        return title.matches("^[a-zA-Z0-9\\s\\-.,!?]+$");
    }
    
    private boolean isValidBlogContent(String content) {
        if (content == null || content.trim().isEmpty() || content.length() &gt; 2000) {
            return false;
        }
        
        String[] forbiddenPatterns = {"&lt;script", "javascript:", "vbscript:", "&lt;iframe", "onload=", "onerror=", "eval("};
        String lowerContent = content.toLowerCase();
        
        for (String pattern : forbiddenPatterns) {
            if (lowerContent.contains(pattern)) {
                return false;
            }
        }
        
        return content.matches("^[a-zA-Z0-9\\s\\-.,!?\\n\\r]+$");
    }
    
    private boolean isValidReviewerName(String name) {
        if (name == null || name.trim().isEmpty() || name.length() &gt; 50) {
            return false;
        }
        return name.matches("^[a-zA-Z0-9\\s]+$");
    }
    
    private boolean isValidRating(String rating) {
        if (rating == null) {
            return false;
        }
        return rating.matches("^[1-5]$");
    }
    
    private boolean isValidReviewComment(String comment) {
        if (comment == null || comment.trim().isEmpty() || comment.length() &gt; 500) {
            return false;
        }
        
        String[] forbiddenWords = {"script", "javascript", "vbscript", "onload", "onerror"};
        String lowerComment = comment.toLowerCase();
        
        for (String word : forbiddenWords) {
            if (lowerComment.contains(word)) {
                return false;
            }
        }
        
        return comment.matches("^[a-zA-Z0-9\\s\\-.,!?]+$");
    }
    
    private boolean isValidAnnouncementMessage(String message) {
        if (message == null || message.trim().isEmpty() || message.length() &gt; 300) {
            return false;
        }
        return message.matches("^[a-zA-Z0-9\\s\\-.,!?]+$");
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
    
    private void setSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
    }
}