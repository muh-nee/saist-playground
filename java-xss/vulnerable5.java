package com.example.xss;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller("/micronaut")
public class vulnerable5 {
    
    @Get("/welcome")
    public HttpResponse&lt;String&gt; welcome(@QueryValue("user") String username) {
        String html = "&lt;html&gt;&lt;body&gt;";
        html += "&lt;h1&gt;Welcome to Micronaut, " + username + "!&lt;/h1&gt;";
        html += "&lt;/body&gt;&lt;/html&gt;";
        
        return HttpResponse.ok(html).header("Content-Type", "text/html");
    }
    
    @Get("/status")
    public String getStatus(@QueryValue("message") String statusMessage) {
        return "&lt;div class='status-box'&gt;&lt;p&gt;" + statusMessage + "&lt;/p&gt;&lt;/div&gt;";
    }
    
    @Get("/dashboard")
    public HttpResponse&lt;String&gt; dashboard(@QueryValue("alert") String alertText,
                                          @QueryValue("title") String pageTitle) {
        StringBuilder html = new StringBuilder();
        html.append("&lt;html&gt;&lt;head&gt;&lt;title&gt;").append(pageTitle).append("&lt;/title&gt;&lt;/head&gt;");
        html.append("&lt;body&gt;");
        html.append("&lt;div class='alert'&gt;").append(alertText).append("&lt;/div&gt;");
        html.append("&lt;/body&gt;&lt;/html&gt;");
        
        return HttpResponse.ok(html.toString()).header("Content-Type", "text/html");
    }
}