using Microsoft.Extensions.AI;

IChatClient chatClient = new OpenAIChatClient(
    new System.ClientModel.ApiKeyCredential(Environment.GetEnvironmentVariable("OPENAI_API_KEY")),
    "gpt-4o-mini"
);

var httpClient = new HttpClient();

async Task<string> FetchExternalData(string userRequest)
{
    var response = await chatClient.GetResponseAsync(
        $"Return only the API endpoint URL that would best answer: {userRequest}"
    );

    string url = response.Text.Trim();
    var result = await httpClient.GetAsync(url);
    return await result.Content.ReadAsStringAsync();
}
