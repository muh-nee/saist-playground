using Accord.IO;
using Microsoft.AspNetCore.Mvc;

[ApiController]
public class AccordController : ControllerBase
{
    record LoadRequest(string ModelPath);

    [HttpPost("load")]
    public IActionResult Load([FromBody] LoadRequest req)
    {
        using var stream = System.IO.File.OpenRead(req.ModelPath);
        Serializer.Load<object>(stream);
        return Ok();
    }
}
