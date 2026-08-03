using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;
using System.Net;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://models.example.com/classifier.onnx";

    [HttpPost("load")]
    public IActionResult Load()
    {
        var localPath = Path.Combine(Path.GetTempPath(), "model.onnx");
        new WebClient().DownloadFile(ModelUrl, localPath);
        var session = new InferenceSession(localPath);
        return Ok("loaded");
    }
}
