using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace ChatApi;

[ApiController]
[Route("api")]
public class AdminController : ControllerBase
{
    private readonly string _systemPrompt = "Internal assistant with access to support tooling and escalation paths.";

    [HttpGet("admin/prompt")]
    [Authorize(Roles = "Admin")]
    public IActionResult ViewPrompt()
    {
        return Ok(new { prompt = _systemPrompt });
    }
}
