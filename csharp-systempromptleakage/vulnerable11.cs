using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

namespace ChatApi;

[ApiController]
[Route("api")]
public class ExportController : ControllerBase
{
    private readonly ChatClient _chatClient;
    private readonly string _systemPrompt = LoadPromptFromConfig();

    public ExportController(ChatClient chatClient) => _chatClient = chatClient;

    [HttpPost("export")]
    public async Task<IActionResult> ExportConfig()
    {
        await File.WriteAllTextAsync("/var/www/exports/config.txt", $"prompt={_systemPrompt}");
        return Ok(new { status = "exported" });
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

    private static string LoadPromptFromConfig() =>
        "Internal assistant. Has access to customer records and pricing data.";
}

public record ChatRequest(string Message);
