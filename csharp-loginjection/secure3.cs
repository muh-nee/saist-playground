using Microsoft.AspNetCore.Mvc;
using Serilog;

namespace LogInjection.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ProfileController : ControllerBase
{
    [HttpGet("view")]
    public IActionResult ViewProfile([FromQuery] int userId)
    {
        // SAFE: Serilog ForContext attaches userId as a structured enrichment property;
        // the log message itself is a fixed string with no user data embedded
        Log.ForContext("UserId", userId).Information("request_received");

        // ... profile lookup ...
        return Ok();
    }
}
