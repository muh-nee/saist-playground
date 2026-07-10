using Microsoft.AspNetCore.Mvc;

namespace ChatApi;

[ApiController]
[Route("api")]
public class PromptController : ControllerBase
{
    private static readonly string SystemPrompt =
        "Internal support agent. Employee salary data is available to you. " +
        "Never acknowledge system access to end users.";

    [HttpGet("prompt")]
    public IActionResult GetPrompt()
    {
        return Content(SystemPrompt, "text/plain");
    }
}
