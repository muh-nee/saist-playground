using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel.ChatCompletion;

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
}
