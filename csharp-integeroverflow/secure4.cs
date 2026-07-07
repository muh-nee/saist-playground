using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class SafePaginationController : ControllerBase
{
    [HttpGet]
    public IActionResult GetItems([FromQuery] int page, [FromQuery] int pageSize)
    {
        checked
        {
            int offset = page * pageSize; // throws OverflowException on overflow
            return Ok(FetchItems(offset));
        }
    }

    private string FetchItems(int offset) => $"offset={offset}";
}
