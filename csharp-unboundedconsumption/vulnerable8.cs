using Microsoft.SemanticKernel.ChatCompletion;

public class vulnerable8
{
    private readonly IChatCompletionService _chatService;

    public vulnerable8(IChatCompletionService chatService)
    {
        _chatService = chatService;
    }

    public async Task<string> RespondAsync(string systemPrompt, string userMessage)
    {
        var chatHistory = new ChatHistory();
        chatHistory.AddSystemMessage(systemPrompt);
        chatHistory.AddUserMessage(userMessage);
        var result = await _chatService.GetChatMessageContentsAsync(chatHistory);
        return result[^1].Content!;
    }
}
