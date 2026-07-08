using Microsoft.AspNetCore.Mvc;
using System.IO;

[ApiController]
[Route("api")]
public class DocumentController : ControllerBase
{
    [HttpPost("upload")]
    public IActionResult Save([FromForm] string folder, [FromForm] string filename)
    {
        var fullPath = Path.Combine("/uploads", folder, filename);
        using var stream = new FileStream(fullPath, FileMode.Create);
        return Ok();
    }

    [HttpGet("report")]
    public IActionResult GetReport()
    {
        var name = Request.Cookies["reportName"] ?? "";
        var path = Path.Combine("/reports", name);
        return PhysicalFile(path, "application/pdf");
    }
}
