using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace LogInjection.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AuthController : ControllerBase
{
    private readonly ILogger<AuthController> _logger;

    public AuthController(ILogger<AuthController> logger)
    {
        _logger = logger;
    }

    [HttpPost("login")]
    public IActionResult Login([FromForm] string username, [FromForm] string password)
    {
        // SAFE: CRLF characters stripped before the value is used in a log message,
        // neutralising the log injection payload even though concatenation is still used
        var sanitized = username.Replace("\r", "").Replace("\n", "");
        _logger.LogInformation("Login: " + sanitized);

        // ... authentication logic ...
        return Ok();
    }
}
