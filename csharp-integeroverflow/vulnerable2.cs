using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class CastController : ControllerBase
{
    [HttpGet]
    public IActionResult Process([FromQuery] long value)
    {
        int id = (int)value; // silently truncates if value > int.MaxValue
        return Ok(id);
    }
}
