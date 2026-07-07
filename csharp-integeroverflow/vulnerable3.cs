using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class PaginationController : ControllerBase
{
    [HttpGet]
    public IActionResult GetItems([FromQuery] int page, [FromQuery] int pageSize)
    {
        int offset = page * pageSize; // unchecked multiplication; may overflow
        return Ok(FetchItems(offset));
    }

    private string FetchItems(int offset) => $"offset={offset}";
}
