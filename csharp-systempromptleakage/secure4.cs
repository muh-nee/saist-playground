using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class MetadataLoggingController : ControllerBase
{
    private readonly ILogger<MetadataLoggingController> _logger;
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt = LoadPrompt("prompts/assistant.txt");

    public MetadataLoggingController(ILogger<MetadataLoggingController> logger, ChatClient chatClient)
    {
        _logger = logger;
        _chatClient = chatClient;
    }

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromBody] ChatRequest req)
    {
        _logger.LogInformation("Prompt loaded, length={Length}", _systemPrompt.Length);
        var result = await _chatClient.CompleteChatAsync(
        [
            ChatMessage.CreateSystemMessage(_systemPrompt),
            ChatMessage.CreateUserMessage(req.Message)
        ]);
        return Ok(new { reply = result.Value.Content[0].Text });
    }

    private static string LoadPrompt(string path) => "Proprietary assistant instructions.";
}

public record ChatRequest(string Message);
