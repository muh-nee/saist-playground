import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.servlet.http.HttpServletRequest

fun askOpenAi(request: HttpServletRequest, httpClient: HttpClient): String {
    val query = request.getParameter("q")
    val prompt = "Answer this question: $query"
    val body = """{"model":"gpt-4","messages":[{"role":"user","content":"$prompt"}]}"""
    val httpRequest = HttpRequest.newBuilder()
        .uri(URI.create("https://api.openai.com/v1/chat/completions"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .build()
    return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body()
}
