using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace LogInjection.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ProfileController : ControllerBase
{
    private readonly ILogger<ProfileController> _logger;

    public ProfileController(ILogger<ProfileController> logger)
    {
        _logger = logger;
    }

    [HttpGet("view")]
    public IActionResult ViewProfile([FromQuery] string username)
    {
        // VULNERABLE: user input passed directly as the entire log message string
        _logger.LogInformation(username);

        // ... profile lookup ...
        return Ok();
    }
}
