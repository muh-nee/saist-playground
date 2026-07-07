using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class DataController : ControllerBase
{
    [HttpPost]
    public byte[] Process([FromBody] DataRequest req)
    {
        int total = req.Count + req.Extra; // unchecked addition; may overflow
        return new byte[total];
    }
}

public record DataRequest(int Count, int Extra);
