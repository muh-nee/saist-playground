using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class DiagnosticController : ControllerBase
{
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt =
        "Confidential: billing assistant with access to payment processor API keys.";

    public DiagnosticController(ChatClient chatClient) => _chatClient = chatClient;

    [HttpGet("debug")]
    public IActionResult Debug()
    {
        return new JsonResult(new { model = "gpt-4o", prompt = _systemPrompt });
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
