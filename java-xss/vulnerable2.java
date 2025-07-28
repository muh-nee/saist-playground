package com.example.xss;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class vulnerable2 {
    
    @GetMapping("/profile")
    public String showProfile(@RequestParam("name") String userName, Model model) {
        
        model.addAttribute("userName", userName);
        model.addAttribute("welcomeMessage", 
            "&lt;h2&gt;Welcome back, " + userName + "!&lt;/h2&gt;");
        
        return "profile";
    }
    
    @GetMapping("/comment")
    public String showComment(@RequestParam("text") String commentText, Model model) {
        
        String htmlContent = "&lt;div class='comment'&gt;" + commentText + "&lt;/div&gt;";
        model.addAttribute("commentHtml", htmlContent);
        
        return "comment";
    }
}