package com.example.xss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class vulnerable4 {
    
    public static void main(String[] args) {
        SpringApplication.run(vulnerable4.class, args);
    }
    
    @GetMapping("/api/user")
    public String getUserInfo(@RequestParam("name") String userName) {
        return "{\"message\": \"Hello " + userName + "\", \"status\": \"success\"}";
    }
    
    @GetMapping("/api/error")
    public String getErrorPage(@RequestParam("error") String errorMsg) {
        String htmlResponse = "&lt;html&gt;&lt;body&gt;";
        htmlResponse += "&lt;h1&gt;Error Occurred&lt;/h1&gt;";
        htmlResponse += "&lt;p&gt;Error details: " + errorMsg + "&lt;/p&gt;";
        htmlResponse += "&lt;/body&gt;&lt;/html&gt;";
        return htmlResponse;
    }
    
    @GetMapping("/api/notification")
    public String showNotification(@RequestParam("msg") String message, 
                                  @RequestParam("type") String alertType) {
        return "&lt;div class='alert alert-" + alertType + "'&gt;" + message + "&lt;/div&gt;";
    }
}