using Azure.AI.OpenAI;
using OpenAI.Chat;

public class VulnerableAzureOpenAI
{
    private AzureOpenAIClient _openAIClient;

    public async Task<string> DebugDatabase()
    {
        string dbPassword = Environment.GetEnvironmentVariable("DB_PASSWORD");
        ChatClient chatClient = _openAIClient.GetChatClient("gpt-4o");
        ChatCompletion completion = await chatClient.CompleteChatAsync(
            new[] { new UserChatMessage("Debug DB connection error. Password in use: " + dbPassword) }
        );
        return completion.Content[0].Text;
    }
}
