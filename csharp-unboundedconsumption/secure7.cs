using Microsoft.Extensions.AI;

public class secure7
{
    private readonly IChatClient _chatClient;

    public secure7(IChatClient chatClient)
    {
        _chatClient = chatClient;
    }

    public async Task<string> RespondAsync(string userMessage)
    {
        var options = new ChatOptions();
        options.MaxOutputTokens = 1024;
        var response = await _chatClient.CompleteAsync(
            [new ChatMessage(ChatRole.User, userMessage)],
            options);
        return response.Message.Text!;
    }
}
