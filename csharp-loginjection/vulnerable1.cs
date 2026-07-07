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
        // VULNERABLE: user-supplied username is concatenated directly into the log message string
        _logger.LogInformation("Login attempt for user: " + username);

        // ... authentication logic ...
        return Ok();
    }
}
