using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace LogInjection.Controllers;

[ApiController]
[Route("api/[controller]")]
public class OrderController : ControllerBase
{
    private readonly ILogger<OrderController> _logger;

    public OrderController(ILogger<OrderController> logger)
    {
        _logger = logger;
    }

    [HttpPost("process")]
    public IActionResult ProcessOrder([FromForm] int userId)
    {
        // SAFE: ILogger.BeginScope attaches userId as a structured ambient property on every log
        // call within the scope; the message itself is a fixed string with no user data
        using (_logger.BeginScope(new Dictionary<string, object> { { "UserId", userId } }))
        {
            _logger.LogInformation("order_processed");
        }

        return Ok();
    }
}
