using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class LlmOutputController : ControllerBase
{
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt = "You are a helpful assistant.";

    public LlmOutputController(ChatClient chatClient) => _chatClient = chatClient;

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromBody] ChatRequest req)
    {
        var result = await _chatClient.CompleteChatAsync(
        [
            ChatMessage.CreateSystemMessage(_systemPrompt),
            ChatMessage.CreateUserMessage(req.Message)
        ]);
        var llmOutput = result.Value.Content[0].Text;
        return Ok(new { reply = llmOutput });
    }
}

public record ChatRequest(string Message);
