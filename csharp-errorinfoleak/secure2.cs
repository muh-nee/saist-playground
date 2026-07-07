using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

[ApiController]
[Route("[controller]")]
public class SecureOrderController : ControllerBase
{
    private readonly IOrderService _service;
    private readonly ILogger<SecureOrderController> _logger;

    public SecureOrderController(IOrderService service, ILogger<SecureOrderController> logger)
    {
        _service = service;
        _logger = logger;
    }

    [HttpPost]
    public IActionResult Create([FromBody] CreateOrderRequest req)
    {
        try
        {
            _service.Create(req);
            return Ok();
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Create order failed");
            return Problem("internal server error");
        }
    }
}
