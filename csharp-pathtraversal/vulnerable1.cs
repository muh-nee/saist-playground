using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("api")]
public class FileController : ControllerBase
{
    [HttpGet("file")]
    public IActionResult GetFile([FromQuery] string name)
    {
        var content = System.IO.File.ReadAllText($"/var/data/{name}");
        return Ok(content);
    }

    [HttpGet("download")]
    public IActionResult Download([FromHeader(Name = "X-Filename")] string filename)
    {
        var path = System.IO.Path.Combine("/uploads", filename);
        var bytes = System.IO.File.ReadAllBytes(path);
        return File(bytes, "application/octet-stream");
    }
}
