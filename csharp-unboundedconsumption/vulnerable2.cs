using OpenAI.Chat;

public class vulnerable2
{
    private readonly ChatClient _client = new ChatClient(
        "gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

    public async Task<string> SummarizeAsync(string text)
    {
        var options = new ChatCompletionOptions
        {
            Temperature = 0.3f
        };
        var result = await _client.CompleteChatAsync(
            [ChatMessage.CreateUserMessage($"Summarize: {text}")],
            options);
        return result.Value.Content[0].Text;
    }
}
