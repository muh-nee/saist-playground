using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;
using System.Net;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://models.example.com/pipeline.zip";

    [HttpPost("load")]
    public IActionResult Load()
    {
        var localPath = Path.Combine(Path.GetTempPath(), "pipeline.zip");
        new WebClient().DownloadFile(ModelUrl, localPath);
        var mlContext = new MLContext();
        var model = mlContext.Model.Load(localPath, out var schema);
        return Ok("loaded");
    }
}
