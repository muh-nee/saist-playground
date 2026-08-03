using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://storage.example.com/models/detector.onnx";
    private readonly HttpClient _httpClient;

    public ModelController(HttpClient httpClient) => _httpClient = httpClient;

    [HttpPost("load")]
    public async Task<IActionResult> Load()
    {
        var modelStream = await _httpClient.GetStreamAsync(ModelUrl);
        var localPath = Path.GetTempFileName();
        using (var fs = File.OpenWrite(localPath))
        {
            await modelStream.CopyToAsync(fs);
        }
        var session = new InferenceSession(localPath);
        return Ok("loaded");
    }
}
