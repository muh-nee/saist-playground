using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;
using System.Net;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://models.example.com/embedder.onnx";

    [HttpPost("load")]
    public IActionResult Load()
    {
        var modelBytes = new WebClient().DownloadData(ModelUrl);
        var session = new InferenceSession(modelBytes);
        return Ok("loaded");
    }
}
