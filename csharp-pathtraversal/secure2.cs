using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.IO;

[ApiController]
[Route("api")]
public class DocumentController : ControllerBase
{
    private static readonly HashSet<string> Allowed = new() { "manual.pdf", "readme.txt" };
    private const string BaseDir = "/uploads";

    [HttpGet("document")]
    public IActionResult GetDocument([FromQuery] string name)
    {
        if (!Allowed.Contains(name))
            return StatusCode(403);
        var bytes = System.IO.File.ReadAllBytes(Path.Combine(BaseDir, name));
        return File(bytes, "application/octet-stream");
    }
}
