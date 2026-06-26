using OpenAI.Chat;

public class SecureApiKeyAuthOnly
{
    public async Task<string> Summarize()
    {
        string apiKey = Environment.GetEnvironmentVariable("OPENAI_API_KEY");
        ChatClient chatClient = new ChatClient("gpt-4o", new ApiKeyCredential(apiKey));
        ChatCompletion completion = await chatClient.CompleteChatAsync(
            new[] { new UserChatMessage("Summarize this document.") }
        );
        return completion.Content[0].Text;
    }
}
