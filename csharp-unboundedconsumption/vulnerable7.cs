using Azure.AI.OpenAI;
using OpenAI.Chat;

public class vulnerable7
{
    private readonly ChatClient _client;

    public vulnerable7(AzureOpenAIClient azureClient)
    {
        _client = azureClient.GetChatClient("gpt-4o");
    }

    public async Task<string> ChatAsync(string userMessage)
    {
        var result = await _client.CompleteChatAsync(
            [ChatMessage.CreateUserMessage(userMessage)]);
        return result.Value.Content[0].Text;
    }
}
