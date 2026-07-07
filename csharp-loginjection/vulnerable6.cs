using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace LogInjection.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ReportController : ControllerBase
{
    private readonly ILogger<ReportController> _logger;

    public ReportController(ILogger<ReportController> logger)
    {
        _logger = logger;
    }

    [HttpGet("run")]
    public IActionResult RunReport([FromQuery] string query)
    {
        // VULNERABLE: ILogger.Log overload with concatenated user-controlled query string
        _logger.Log(LogLevel.Information, "Query: " + query);

        // ... report execution ...
        return Ok();
    }
}
