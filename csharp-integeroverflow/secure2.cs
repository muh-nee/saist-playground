using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class SafeCastController : ControllerBase
{
    [HttpGet]
    public IActionResult Process([FromQuery] long value)
    {
        if (value > int.MaxValue || value < int.MinValue)
            return BadRequest("value out of range");
        int id = (int)value;
        return Ok(id);
    }
}
