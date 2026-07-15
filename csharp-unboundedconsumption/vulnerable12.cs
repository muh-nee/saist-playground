using OpenAI.Chat;

public class vulnerable12
{
    private readonly ChatClient _client = new ChatClient(
        "gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

    public async IAsyncEnumerable<string> StreamAnswerAsync(string userMessage)
    {
        await foreach (var update in _client.CompleteChatStreamingAsync(
            [ChatMessage.CreateUserMessage(userMessage)]))
        {
            if (update.ContentUpdate.Count > 0)
                yield return update.ContentUpdate[0].Text;
        }
    }
}
