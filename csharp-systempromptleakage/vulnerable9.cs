using Microsoft.AspNetCore.Mvc;

namespace ChatApi;

[ApiController]
[Route("api")]
public class DiagnosticController : ControllerBase
{
    private readonly string _systemPrompt =
        "Confidential: billing assistant with access to payment processor API keys.";

    [HttpGet("debug")]
    public IActionResult Debug()
    {
        return new JsonResult(new { model = "gpt-4o", prompt = _systemPrompt });
    }
}
