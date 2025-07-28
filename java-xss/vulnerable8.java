package com.example.xss;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

public class vulnerable8 extends ActionSupport implements ServletRequestAware {
    
    private HttpServletRequest request;
    private String userName;
    private String userMessage;
    private String searchQuery;
    private String htmlContent;
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    public String displayUser() {
        userName = request.getParameter("user");
        
        htmlContent = "&lt;div class='user-info'&gt;";
        htmlContent += "&lt;h2&gt;Welcome " + userName + "!&lt;/h2&gt;";
        htmlContent += "&lt;p&gt;User: " + userName + "&lt;/p&gt;";
        htmlContent += "&lt;/div&gt;";
        
        return SUCCESS;
    }
    
    public String processMessage() {
        userMessage = request.getParameter("message");
        String messageType = request.getParameter("type");
        
        htmlContent = "&lt;div class='message " + messageType + "'&gt;";
        htmlContent += userMessage;
        htmlContent += "&lt;/div&gt;";
        
        return SUCCESS;
    }
    
    public String searchResults() {
        searchQuery = request.getParameter("q");
        String category = request.getParameter("category");
        
        htmlContent = "&lt;h1&gt;Search Results&lt;/h1&gt;";
        htmlContent += "&lt;p&gt;You searched for: " + searchQuery + " in " + category + "&lt;/p&gt;";
        htmlContent += "&lt;div&gt;Results for '" + searchQuery + "'&lt;/div&gt;";
        
        return SUCCESS;
    }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }
    
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    
    public String getHtmlContent() { return htmlContent; }
    public void setHtmlContent(String htmlContent) { this.htmlContent = htmlContent; }
}