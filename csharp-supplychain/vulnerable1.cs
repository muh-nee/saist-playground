using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://models.example.com/classifier.onnx";
    private readonly HttpClient _httpClient;

    public ModelController(HttpClient httpClient) => _httpClient = httpClient;

    [HttpPost("load")]
    public async Task<IActionResult> Load()
    {
        var modelBytes = await _httpClient.GetByteArrayAsync(ModelUrl);
        var session = new InferenceSession(modelBytes);
        return Ok("loaded");
    }
}
