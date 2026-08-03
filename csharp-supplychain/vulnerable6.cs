using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://cdn.example.com/model.onnx";
    private readonly HttpClient _httpClient;

    public ModelController(HttpClient httpClient) => _httpClient = httpClient;

    [HttpPost("update")]
    public async Task<IActionResult> Update()
    {
        var raw = await _httpClient.GetByteArrayAsync(ModelUrl);
        var modelBytes = raw;
        var session = new InferenceSession(modelBytes, new SessionOptions());
        return Ok("updated");
    }
}
