// Vulnerable: langchain4j output used as URL in HTTP request (SSRF)
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class vulnerable7 {
    private final ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String fetchResource(String description) throws Exception {
        String url = model.generate("Return only the URL for: " + description).trim();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body(); // sink: LLM-controlled URL enables SSRF
    }
}
