package com.example.xss;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class vulnerable7 {
    
    @GetMapping("/blog")
    public String showBlogPost(@RequestParam("title") String postTitle,
                              @RequestParam("content") String postContent,
                              Model model) {
        
        model.addAttribute("postTitle", postTitle);
        model.addAttribute("postContent", postContent);
        model.addAttribute("rawHtmlContent", 
            "&lt;div class='blog-post'&gt;&lt;h1&gt;" + postTitle + "&lt;/h1&gt;&lt;p&gt;" + postContent + "&lt;/p&gt;&lt;/div&gt;");
        
        return "blog";
    }
    
    @GetMapping("/review")
    public String showReview(@RequestParam("reviewer") String reviewerName,
                            @RequestParam("rating") String rating,
                            @RequestParam("comment") String reviewComment,
                            Model model) {
        
        String reviewHtml = "&lt;div class='review'&gt;";
        reviewHtml += "&lt;h3&gt;Review by " + reviewerName + "&lt;/h3&gt;";
        reviewHtml += "&lt;div class='rating'&gt;" + rating + " stars&lt;/div&gt;";
        reviewHtml += "&lt;div class='comment'&gt;" + reviewComment + "&lt;/div&gt;";
        reviewHtml += "&lt;/div&gt;";
        
        model.addAttribute("reviewHtml", reviewHtml);
        model.addAttribute("reviewerName", reviewerName);
        
        return "review";
    }
    
    @GetMapping("/announcement")
    public String showAnnouncement(@RequestParam("message") String announcementMsg,
                                  @RequestParam("type") String messageType,
                                  Model model) {
        
        model.addAttribute("announcement", announcementMsg);
        model.addAttribute("messageType", messageType);
        model.addAttribute("fullMessage", 
            "&lt;div class='announcement " + messageType + "'&gt;" + announcementMsg + "&lt;/div&gt;");
        
        return "announcement";
    }
}