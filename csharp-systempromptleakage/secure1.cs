using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class ChatController : ControllerBase
{
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt = "You are a helpful customer support assistant.";

    public ChatController(ChatClient chatClient) => _chatClient = chatClient;

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromBody] ChatRequest req)
    {
        var result = await _chatClient.CompleteChatAsync(
        [
            ChatMessage.CreateSystemMessage(_systemPrompt),
            ChatMessage.CreateUserMessage(req.Message)
        ], new ChatCompletionOptions { MaxOutputTokenCount = 1024 });
        return Ok(new { reply = result.Value.Content[0].Text, disclaimer = "AI-generated content. Verify independently." });
    }
}

public record ChatRequest(string Message);
