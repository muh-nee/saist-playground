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
        // SAFE: ILogger named placeholder — username is stored as a structured property,
        // never interpolated into the message string itself
        _logger.LogInformation("login_attempt for user {Username}", username);

        // ... authentication logic ...
        return Ok();
    }
}
