using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class LlmController : ControllerBase
{
    private readonly ChatClient _chatClient;
    private readonly string _systemInstructions =
        "You assist with HR queries. Employee salary data is available to you.";

    public LlmController(ChatClient chatClient) => _chatClient = chatClient;

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromBody] ChatRequest req)
    {
        Console.WriteLine($"[DEBUG] systemInstructions={_systemInstructions}");
        var result = await _chatClient.CompleteChatAsync(
        [
            ChatMessage.CreateSystemMessage(_systemInstructions),
            ChatMessage.CreateUserMessage(req.Message)
        ]);
        return Ok(new { reply = result.Value.Content[0].Text });
    }
}

public record ChatRequest(string Message);
