using Microsoft.Extensions.AI;

public class secure8
{
    private readonly IChatClient _chatClient;

    public secure8(IChatClient chatClient)
    {
        _chatClient = chatClient;
    }

    public async Task<string> GetResponseAsync(string userMessage)
    {
        var options = new ChatOptions
        {
            MaxOutputTokens = 2048
        };
        var response = await _chatClient.GetResponseAsync(
            [new ChatMessage(ChatRole.User, userMessage)],
            options);
        return response.Text;
    }
}
