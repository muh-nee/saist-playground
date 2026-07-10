using OpenAI.Chat;
using Serilog;

namespace ChatApi;

public class RequestProcessor
{
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt = Environment.GetEnvironmentVariable("SYSTEM_PROMPT")!;

    public RequestProcessor(ChatClient chatClient)
    {
        _chatClient = chatClient;
    }

    public async Task<string> ProcessRequestAsync(string requestId, string userMessage)
    {
        Log.Information("Handling {@Request}", new { requestId, _systemPrompt, userMessage });
        var result = await _chatClient.CompleteChatAsync(
        [
            ChatMessage.CreateSystemMessage(_systemPrompt),
            ChatMessage.CreateUserMessage(userMessage)
        ]);
        return result.Value.Content[0].Text;
    }
}
