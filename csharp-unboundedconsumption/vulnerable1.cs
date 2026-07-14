using OpenAI.Chat;

public class vulnerable1
{
    private readonly ChatClient _client = new ChatClient(
        "gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

    public async Task<string> AnswerAsync(string userMessage)
    {
        var result = await _client.CompleteChatAsync(
            [ChatMessage.CreateUserMessage(userMessage)]);
        return result.Value.Content[0].Text;
    }
}
