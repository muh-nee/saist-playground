package main;

import org.springframework.ai.tool.annotation.Tool;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;

class SafeWebTools {
    private static final Set<String> ALLOWED_HOSTS = Set.of("api.internal.example.com");

    @Tool("Fetch data from an approved internal API")
    public String fetchInternal(String url) throws IOException, InterruptedException {
        String host = URI.create(url).getHost();
        if (!ALLOWED_HOSTS.contains(host)) {
            throw new SecurityException("host not in allowlist");
        }
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        return HttpClient.newHttpClient()
                .send(req, HttpResponse.BodyHandlers.ofString())
                .body();
    }
}
