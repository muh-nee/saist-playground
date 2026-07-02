package main;

import org.springframework.ai.tool.annotation.Tool;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class WebTools {
    @Tool("Fetch the content of a URL")
    public String fetchUrl(String url) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        return HttpClient.newHttpClient()
                .send(req, HttpResponse.BodyHandlers.ofString())
                .body();
    }
}
