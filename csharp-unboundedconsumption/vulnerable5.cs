using Microsoft.Extensions.AI;

public class vulnerable5
{
    private readonly IChatClient _chatClient;

    public vulnerable5(IChatClient chatClient)
    {
        _chatClient = chatClient;
    }

    public async Task<string> RespondAsync(string userMessage)
    {
        var response = await _chatClient.CompleteAsync(
            [new ChatMessage(ChatRole.User, userMessage)]);
        return response.Message.Text!;
    }
}
