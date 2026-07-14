using Microsoft.SemanticKernel.ChatCompletion;
using Microsoft.SemanticKernel.Connectors.OpenAI;

public class secure6
{
    private readonly IChatCompletionService _chatService;

    public secure6(IChatCompletionService chatService)
    {
        _chatService = chatService;
    }

    public async Task<string> RespondAsync(string systemPrompt, string userMessage)
    {
        var chatHistory = new ChatHistory();
        chatHistory.AddSystemMessage(systemPrompt);
        chatHistory.AddUserMessage(userMessage);
        var settings = new OpenAIPromptExecutionSettings { MaxTokens = 512 };
        var result = await _chatService.GetChatMessageContentsAsync(chatHistory, settings);
        return result[^1].Content!;
    }
}
