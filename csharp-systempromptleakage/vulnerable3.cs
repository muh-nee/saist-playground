using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class ChatController : ControllerBase
{
    private readonly ILogger<ChatController> _logger;
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt = LoadPromptFromFile("prompts/assistant.txt");

    public ChatController(ILogger<ChatController> logger, ChatClient chatClient)
    {
        _logger = logger;
        _chatClient = chatClient;
    }

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromBody] ChatRequest req)
    {
        _logger.LogInformation("Sending LLM request, system prompt: {SystemPrompt}", _systemPrompt);
        var result = await _chatClient.CompleteChatAsync(
        [
            ChatMessage.CreateSystemMessage(_systemPrompt),
            ChatMessage.CreateUserMessage(req.Message)
        ]);
        return Ok(new { reply = result.Value.Content[0].Text });
    }

    private static string LoadPromptFromFile(string path) =>
        "Internal support agent. Has access to HR records and payroll data.";
}

public record ChatRequest(string Message);
