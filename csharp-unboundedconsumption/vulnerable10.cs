using Microsoft.Extensions.AI;

public class vulnerable10
{
    private readonly IChatClient _chatClient;

    public vulnerable10(IChatClient chatClient)
    {
        _chatClient = chatClient;
    }

    public async Task<string> SummarizeAsync(string document)
    {
        var response = await _chatClient.GetResponseAsync(
            [new ChatMessage(ChatRole.User, $"Summarize: {document}")]);
        return response.Text;
    }
}
