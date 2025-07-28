package com.example.xss;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/jaxrs")
public class vulnerable6 {
    
    @GET
    @Path("/user")
    @Produces(MediaType.TEXT_HTML)
    public Response getUserProfile(@QueryParam("name") String userName) {
        String html = "&lt;html&gt;&lt;body&gt;";
        html += "&lt;h2&gt;User Profile&lt;/h2&gt;";
        html += "&lt;p&gt;Name: " + userName + "&lt;/p&gt;";
        html += "&lt;/body&gt;&lt;/html&gt;";
        
        return Response.ok(html).build();
    }
    
    @GET
    @Path("/search")
    @Produces(MediaType.TEXT_HTML)
    public String searchResults(@QueryParam("q") String query, 
                               @QueryParam("category") String category) {
        String resultsHtml = "&lt;div class='search-results'&gt;";
        resultsHtml += "&lt;h3&gt;Search results for '" + query + "' in " + category + "&lt;/h3&gt;";
        resultsHtml += "&lt;p&gt;No results found for: " + query + "&lt;/p&gt;";
        resultsHtml += "&lt;/div&gt;";
        
        return resultsHtml;
    }
    
    @GET
    @Path("/redirect")
    @Produces(MediaType.TEXT_HTML)
    public Response redirectPage(@QueryParam("url") String redirectUrl,
                                @QueryParam("message") String message) {
        String html = "&lt;html&gt;&lt;body&gt;";
        html += "&lt;p&gt;" + message + "&lt;/p&gt;";
        html += "&lt;p&gt;Redirecting to: &lt;a href='" + redirectUrl + "'&gt;" + redirectUrl + "&lt;/a&gt;&lt;/p&gt;";
        html += "&lt;script&gt;setTimeout(function(){ window.location.href='" + redirectUrl + "'; }, 3000);&lt;/script&gt;";
        html += "&lt;/body&gt;&lt;/html&gt;";
        
        return Response.ok(html).build();
    }
}