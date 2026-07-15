using OpenAI.Chat;

public class secure1
{
    private readonly ChatClient _client = new ChatClient(
        "gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

    public async Task<string> AnswerAsync(string userMessage)
    {
        var options = new ChatCompletionOptions
        {
            MaxOutputTokenCount = 1024
        };
        var result = await _client.CompleteChatAsync(
            [ChatMessage.CreateUserMessage(userMessage)],
            options);
        return result.Value.Content[0].Text;
    }
}