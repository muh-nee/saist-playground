using OpenAI.Chat;

public class secure4
{
    private readonly ChatClient _client;
    private readonly int _maxTokens;

    public secure4(string apiKey, int maxTokens)
    {
        _client = new ChatClient("gpt-4o", apiKey);
        _maxTokens = maxTokens;
    }

    public async Task<string> AnswerAsync(string userMessage)
    {
        var options = new ChatCompletionOptions
        {
            MaxOutputTokenCount = _maxTokens
        };
        var result = await _client.CompleteChatAsync(
            [ChatMessage.CreateUserMessage(userMessage)],
            options);
        return result.Value.Content[0].Text;
    }
}
