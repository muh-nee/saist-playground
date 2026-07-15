using Microsoft.Extensions.AI;

public class vulnerable13
{
    private readonly IChatClient _chatClient;

    public vulnerable13(IChatClient chatClient)
    {
        _chatClient = chatClient;
    }

    public async IAsyncEnumerable<string> StreamAsync(string userMessage)
    {
        await foreach (var update in _chatClient.GetStreamingResponseAsync(
            [new ChatMessage(ChatRole.User, userMessage)]))
        {
            if (update.Text is not null)
                yield return update.Text;
        }
    }
}
