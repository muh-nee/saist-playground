using OpenAI.Chat;

namespace AgentTools;

public class SafeWebFetchAgent
{
    private static readonly HashSet<string> AllowedHosts = new(StringComparer.OrdinalIgnoreCase)
    {
        "api.example.com",
        "data.partner.io"
    };

    private readonly HttpClient _http = new();

    public ChatTool GetFetchTool() => ChatTool.CreateFunctionTool(
        "fetch_url",
        "Fetch data from an approved API endpoint",
        BinaryData.FromObjectAsJson(new
        {
            type = "object",
            properties = new
            {
                url = new { type = "string", description = "The URL to fetch" }
            },
            required = new[] { "url" }
        })
    );

    public async Task<string> FetchUrl(string url)
    {
        var host = new Uri(url).Host;
        if (!AllowedHosts.Contains(host))
            throw new ArgumentException($"host '{host}' is not in the allowlist");

        var response = await _http.GetAsync(url);
        return await response.Content.ReadAsStringAsync();
    }
}
