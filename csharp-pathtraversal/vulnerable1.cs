using System.IO;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("files")]
public class FilesController : ControllerBase
{
    [HttpGet]
    public IActionResult Read([FromQuery] string path)
    {
        return Ok(File.ReadAllText(path));
    }
}
