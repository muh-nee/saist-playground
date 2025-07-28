package com.example.xss;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.servlet.http.HttpServletResponse;

@Controller
@Validated
public class secure2 {
    
    @GetMapping("/profile")
    public String showProfile(
            @RequestParam("name") 
            @Size(max = 50, message = "Name must be less than 50 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Name contains invalid characters")
            String userName, 
            Model model,
            HttpServletResponse response) {
        
        setSecurityHeaders(response);
        
        String sanitizedUserName = HtmlUtils.htmlEscape(userName);
        
        model.addAttribute("userName", sanitizedUserName);
        model.addAttribute("welcomeMessage", "Welcome back, " + sanitizedUserName + "!");
        
        return "profile";
    }
    
    @GetMapping("/comment")
    public String showComment(
            @RequestParam("text") 
            @Size(max = 500, message = "Comment must be less than 500 characters")
            @Pattern(regexp = "^[a-zA-Z0-9\\s\\.,!?\\-]+$", message = "Comment contains invalid characters")
            String commentText, 
            Model model,
            HttpServletResponse response) {
        
        setSecurityHeaders(response);
        
        String sanitizedComment = HtmlUtils.htmlEscape(commentText);
        
        model.addAttribute("commentText", sanitizedComment);
        model.addAttribute("isValidComment", isValidComment(commentText));
        
        return "comment";
    }
    
    private boolean isValidComment(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            return false;
        }
        
        String[] forbiddenWords = {"script", "javascript", "vbscript", "onload", "onerror"};
        String lowerComment = comment.toLowerCase();
        
        for (String word : forbiddenWords) {
            if (lowerComment.contains(word)) {
                return false;
            }
        }
        
        return true;
    }
    
    private void setSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'");
    }
}