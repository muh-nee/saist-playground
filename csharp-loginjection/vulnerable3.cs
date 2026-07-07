using Microsoft.AspNetCore.Mvc;
using NLog;

namespace LogInjection.Controllers;

[ApiController]
[Route("api/[controller]")]
public class OrderController : ControllerBase
{
    private static readonly Logger Logger = LogManager.GetCurrentClassLogger();

    [HttpPost("process")]
    public IActionResult ProcessOrder([FromForm] string orderId)
    {
        // VULNERABLE: NLog Logger.Info with user-controlled orderId concatenated into the message
        Logger.Info("Processing order: " + orderId);

        // ... order processing logic ...
        return Ok();
    }
}
