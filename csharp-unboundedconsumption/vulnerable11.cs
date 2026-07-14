using Microsoft.SemanticKernel.ChatCompletion;
using Microsoft.SemanticKernel.Connectors.OpenAI;

public class vulnerable11
{
    private readonly IChatCompletionService _chatService;

    public vulnerable11(IChatCompletionService chatService)
    {
        _chatService = chatService;
    }

    public async IAsyncEnumerable<string> StreamAsync(string userMessage)
    {
        var chatHistory = new ChatHistory();
        chatHistory.AddUserMessage(userMessage);
        await foreach (var content in _chatService.GetStreamingChatMessageContentsAsync(chatHistory))
        {
            if (content.Content is not null)
                yield return content.Content;
        }
    }
}
