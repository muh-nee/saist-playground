using Microsoft.AspNetCore.Mvc;
using System.IO;

[ApiController]
[Route("api")]
public class FileController : ControllerBase
{
    private readonly string _base = Path.GetFullPath("/var/data");

    [HttpGet("file")]
    public IActionResult GetFile([FromQuery] string name)
    {
        var fullPath = Path.GetFullPath(Path.Combine(_base, name));
        if (!fullPath.StartsWith(_base + Path.DirectorySeparatorChar))
            return Forbid();
        return Ok(System.IO.File.ReadAllText(fullPath));
    }
}
