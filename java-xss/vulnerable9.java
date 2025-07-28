package com.example.xss;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;

public class vulnerable9 extends Controller {
    
    public Result userProfile(Http.Request request) {
        String username = request.getQueryString("name");
        String bio = request.getQueryString("bio");
        
        String html = "&lt;html&gt;&lt;body&gt;";
        html += "&lt;h1&gt;Profile for " + username + "&lt;/h1&gt;";
        html += "&lt;div class='bio'&gt;" + bio + "&lt;/div&gt;";
        html += "&lt;/body&gt;&lt;/html&gt;";
        
        return ok(html).as("text/html");
    }
    
    public Result displayComment(Http.Request request) {
        String comment = request.getQueryString("comment");
        String author = request.getQueryString("author");
        
        String commentHtml = "&lt;div class='comment-box'&gt;";
        commentHtml += "&lt;h3&gt;Comment by " + author + "&lt;/h3&gt;";
        commentHtml += "&lt;p&gt;" + comment + "&lt;/p&gt;";
        commentHtml += "&lt;/div&gt;";
        
        return ok(commentHtml).as("text/html");
    }
    
    public Result errorPage(Http.Request request) {
        String errorMsg = request.getQueryString("error");
        String errorCode = request.getQueryString("code");
        
        String errorHtml = "&lt;html&gt;&lt;head&gt;&lt;title&gt;Error " + errorCode + "&lt;/title&gt;&lt;/head&gt;";
        errorHtml += "&lt;body&gt;&lt;h1&gt;Error " + errorCode + "&lt;/h1&gt;";
        errorHtml += "&lt;p&gt;Error message: " + errorMsg + "&lt;/p&gt;";
        errorHtml += "&lt;/body&gt;&lt;/html&gt;";
        
        return internalServerError(errorHtml).as("text/html");
    }
    
    public Result searchResults(Http.Request request) {
        String query = request.getQueryString("q");
        String filter = request.getQueryString("filter");
        
        String resultsHtml = "&lt;div class='search-container'&gt;";
        resultsHtml += "&lt;h2&gt;Search Results&lt;/h2&gt;";
        resultsHtml += "&lt;p&gt;Query: " + query + " (Filter: " + filter + ")&lt;/p&gt;";
        resultsHtml += "&lt;div class='results'&gt;No results found for: " + query + "&lt;/div&gt;";
        resultsHtml += "&lt;/div&gt;";
        
        return ok(resultsHtml).as("text/html");
    }
}