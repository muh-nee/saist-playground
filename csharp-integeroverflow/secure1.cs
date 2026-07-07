using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class SafeBufferController : ControllerBase
{
    private const int BlockSize = 512;

    [HttpGet]
    public byte[] Allocate([FromQuery] int count)
    {
        return new byte[checked(count * BlockSize)]; // throws OverflowException if overflow
    }
}
