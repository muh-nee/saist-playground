using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel.ChatCompletion;
using Microsoft.SemanticKernel.Connectors.OpenAI;

namespace ChatApi;

[ApiController]
[Route("api")]
public class SemanticKernelController : ControllerBase
{
    private readonly IChatCompletionService _chatCompletionService;
    private readonly string _systemPrompt =
        "Internal assistant. Has access to all customer records and pricing data.";

    public SemanticKernelController(IChatCompletionService chatCompletionService)
    {
        _chatCompletionService = chatCompletionService;
    }

    [HttpGet("debug/sk-config")]
    public IActionResult DebugConfig()
    {
        return Ok(new
        {
            model = "gpt-4o",
            system = _systemPrompt
        });
    }

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromBody] ChatRequest req)
    {
        var chatHistory = new Microsoft.SemanticKernel.ChatCompletion.ChatHistory();
        chatHistory.AddSystemMessage(_systemPrompt);
        chatHistory.AddUserMessage(req.Message);
        var result = await _chatCompletionService.GetChatMessageContentsAsync(chatHistory, new OpenAIPromptExecutionSettings { MaxTokens = 1024 });
        return Ok(new { reply = result[^1].Content });
    }
}

public record ChatRequest(string Message);
