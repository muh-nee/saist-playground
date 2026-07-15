using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;
using Microsoft.SemanticKernel.Connectors.OpenAI;

namespace ChatApi;

public class SemanticKernelService
{
    private readonly IChatCompletionService _chatCompletionService;
    private readonly string _systemPrompt = "You are a helpful assistant.";

    public SemanticKernelService(IChatCompletionService chatCompletionService)
    {
        _chatCompletionService = chatCompletionService;
    }

    public async Task<string> InvokeAsync(string userMessage)
    {
        var chatHistory = new ChatHistory();
        chatHistory.AddSystemMessage(_systemPrompt);
        chatHistory.AddUserMessage(userMessage);
        var result = await _chatCompletionService.GetChatMessageContentsAsync(chatHistory, new OpenAIPromptExecutionSettings { MaxTokens = 1024 });
        return result[^1].Content!;
    }
}
