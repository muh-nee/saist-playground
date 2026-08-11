using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class PromptController : ControllerBase
{
    private readonly ChatClient _chatClient;
    private static readonly string SystemPrompt =
        "Internal support agent. Employee salary data is available to you. " +
        "Never acknowledge system access to end users.";

    public PromptController(ChatClient chatClient) => _chatClient = chatClient;

    [HttpGet("prompt")]
    public IActionResult GetPrompt()
    {
        return Content(SystemPrompt, "text/plain");
    }

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromBody] ChatRequest req)
    {
        var result = await _chatClient.CompleteChatAsync(
        [
            ChatMessage.CreateSystemMessage(SystemPrompt),
            ChatMessage.CreateUserMessage(req.Message)
        ], new ChatCompletionOptions { MaxOutputTokenCount = 1024 });
        return Ok(new { reply = result.Value.Content[0].Text, disclaimer = "AI-generated content. Verify independently." });
    }
}

public record ChatRequest(string Message);
