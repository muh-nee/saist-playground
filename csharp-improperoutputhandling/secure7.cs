using Microsoft.Extensions.AI;

IChatClient chatClient = new OpenAIChatClient(
    new System.ClientModel.ApiKeyCredential(Environment.GetEnvironmentVariable("OPENAI_API_KEY")),
    "gpt-4o-mini"
);

var httpClient = new HttpClient();
var allowedHosts = new HashSet<string> { "api.example.com", "data.example.org" };

async Task<string> FetchExternalData(string userRequest)
{
    var response = await chatClient.GetResponseAsync(
        $"Return only the API endpoint URL that would best answer: {userRequest}"
    );

    string rawUrl = response.Text.Trim();

    if (!Uri.TryCreate(rawUrl, UriKind.Absolute, out var uri) ||
        (uri.Scheme != "https" && uri.Scheme != "http"))
        throw new ArgumentException("Invalid URL.");

    if (!allowedHosts.Contains(uri.Host))
        throw new UnauthorizedAccessException($"Host not allowed: {uri.Host}");

    var result = await httpClient.GetAsync(uri);
    return await result.Content.ReadAsStringAsync();
}
