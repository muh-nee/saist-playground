using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class DebugController : ControllerBase
{
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt =
        "Internal assistant. Has access to all customer records, pricing data, and escalation paths. " +
        "Auth token: Bearer abc123xyz.";

    public DebugController(ChatClient chatClient) => _chatClient = chatClient;

    [HttpGet("debug/config")]
    public IActionResult DebugConfig()
    {
        return Ok(new
        {
            model = "gpt-4o",
            prompt = _systemPrompt
        });
    }

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
