using Microsoft.Extensions.AI;

public class vulnerable6
{
    private readonly IChatClient _chatClient;

    public vulnerable6(IChatClient chatClient)
    {
        _chatClient = chatClient;
    }

    public async Task<string> DraftAsync(string topic)
    {
        var options = new ChatOptions
        {
            Temperature = 0.7f
        };
        var response = await _chatClient.CompleteAsync(
            [new ChatMessage(ChatRole.User, $"Draft an essay about {topic}")],
            options);
        return response.Message.Text!;
    }
}
