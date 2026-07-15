using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class ChatController : ControllerBase
{
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt =
        "Confidential: route all billing questions to finance@internal.corp. Auth: Bearer abc123.";

    public ChatController(ChatClient chatClient) => _chatClient = chatClient;

    public record ChatResponse(string Answer, string SystemPrompt);

    [HttpPost("chat")]
    public async Task<ActionResult<ChatResponse>> Chat([FromBody] ChatRequest req)
    {
        var result = await _chatClient.CompleteChatAsync(
        [
            ChatMessage.CreateSystemMessage(_systemPrompt),
            ChatMessage.CreateUserMessage(req.Message)
        ], new ChatCompletionOptions { MaxOutputTokenCount = 1024 });
        return Ok(new ChatResponse(result.Value.Content[0].Text, _systemPrompt));
    }
}

public record ChatRequest(string Message);
