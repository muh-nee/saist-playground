using Microsoft.AspNetCore.Mvc;
using NLog;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class NLogController : ControllerBase
{
    private static readonly NLog.Logger Logger = LogManager.GetCurrentClassLogger();
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt = LoadConfig();

    public NLogController(ChatClient chatClient) => _chatClient = chatClient;

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromBody] ChatRequest req)
    {
        Logger.Info("Using system prompt: {0}", _systemPrompt);
        var result = await _chatClient.CompleteChatAsync(
        [
            ChatMessage.CreateSystemMessage(_systemPrompt),
            ChatMessage.CreateUserMessage(req.Message)
        ]);
        return Ok(new { reply = result.Value.Content[0].Text });
    }

    private static string LoadConfig() =>
        "Internal ops assistant. Has access to deployment credentials and CI secrets.";
}

public record ChatRequest(string Message);
