using Microsoft.AspNetCore.Mvc;

namespace ChatApi;

[ApiController]
[Route("api")]
public class DebugController : ControllerBase
{
    private readonly string _systemPrompt =
        "Internal assistant. Has access to all customer records, pricing data, and escalation paths. " +
        "Auth token: Bearer abc123xyz.";

    [HttpGet("debug/config")]
    public IActionResult DebugConfig()
    {
        return Ok(new
        {
            model = "gpt-4o",
            prompt = _systemPrompt
        });
    }
}
