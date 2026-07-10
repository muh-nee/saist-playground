using Microsoft.AspNetCore.Mvc;

namespace ChatApi;

[ApiController]
[Route("api")]
public class RedactedPromptController : ControllerBase
{
    private readonly string _systemPrompt = "Proprietary assistant instructions.";

    [HttpGet("debug/prompt-info")]
    public IActionResult PromptInfo()
    {
        return Ok(new
        {
            PromptLength = _systemPrompt.Length,
            PromptPreview = "[REDACTED]"
        });
    }
}
