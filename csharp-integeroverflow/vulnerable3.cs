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

    private static readonly string[] Items = new string[1000];

    private string FetchItems(int offset) => Items[offset];
}
