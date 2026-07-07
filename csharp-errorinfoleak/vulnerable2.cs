using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class OrderController : ControllerBase
{
    private readonly IOrderService _service;

    public OrderController(IOrderService service) => _service = service;

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
            var problem = new ProblemDetails
            {
                Status = 500,
                Detail = ex.Message,
                Title = ex.GetType().Name
            };
            return StatusCode(500, problem);
        }
    }
}

public record CreateOrderRequest(string Item, int Qty);
public interface IOrderService { void Create(CreateOrderRequest req); }
