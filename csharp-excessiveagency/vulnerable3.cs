using OpenAI.Chat;

namespace AgentTools;

public class WebFetchAgent
{
    private readonly HttpClient _http = new();

    public ChatTool GetFetchTool() => ChatTool.CreateFunctionTool(
        "fetch_url",
        "Fetch the contents of a URL",
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
        var response = await _http.GetAsync(url);
        return await response.Content.ReadAsStringAsync();
    }
}
