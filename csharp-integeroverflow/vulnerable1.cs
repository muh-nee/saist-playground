using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class BufferController : ControllerBase
{
    private const int BlockSize = 512;

    [HttpGet]
    public byte[] Allocate([FromQuery] int count)
    {
        return new byte[count * BlockSize]; // count * BlockSize may overflow unchecked
    }
}
