using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;
using Microsoft.Extensions.Options;

public class VulnerableSemanticKernelHistory
{
    private IChatCompletionService _chatCompletionService;
    private IOptions<AppOptions> _options;

    public async Task<string> GetSupport()
    {
        string apiSecret = _options.Value.ApiSecret;
        var history = new ChatHistory();
        history.AddSystemMessage("Internal API secret: " + apiSecret);
        history.AddUserMessage("Help me debug the integration.");
        IReadOnlyList<ChatMessageContent> result = await _chatCompletionService.GetChatMessageContentsAsync(history);
        return result[0].Content;
    }
}

public class AppOptions { public string ApiSecret { get; set; } }
