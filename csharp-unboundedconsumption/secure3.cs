using Microsoft.Extensions.AI;

public class secure3
{
    private readonly IChatClient _chatClient;

    public secure3(IChatClient chatClient)
    {
        _chatClient = chatClient;
    }

    public async Task<string> RespondAsync(string userMessage)
    {
        var options = new ChatOptions
        {
            MaxOutputTokens = 2048
        };
        var response = await _chatClient.CompleteAsync(
            [new ChatMessage(ChatRole.User, userMessage)],
            options);
        return response.Message.Text!;
    }
}
