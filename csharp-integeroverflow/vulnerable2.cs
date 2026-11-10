using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class CastController : ControllerBase
{
    [HttpGet]
    public IActionResult Process([FromQuery] long value)
    {
        int size = (int)value;
        var buffer = new byte[size];
        return Ok(buffer.Length);
    }
}
