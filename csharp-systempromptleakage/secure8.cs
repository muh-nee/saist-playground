using Microsoft.AspNetCore.Mvc;

namespace ChatApi;

[ApiController]
[Route("api")]
public class RoleCheckedController : ControllerBase
{
    private readonly string _systemPrompt = "Internal assistant configuration. Contains sensitive routing logic.";

    [HttpGet("config")]
    public IActionResult GetConfig()
    {
        if (!User.IsInRole("Admin"))
            return Forbid();
        return Ok(new { prompt = _systemPrompt });
    }
}
