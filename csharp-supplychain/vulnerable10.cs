using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private readonly HttpClient _httpClient;

    public ModelController(HttpClient httpClient) => _httpClient = httpClient;

    [HttpPost("load")]
    public async Task<IActionResult> Load([FromQuery] string modelUrl)
    {
        var modelBytes = await _httpClient.GetByteArrayAsync(modelUrl);
        var session = new InferenceSession(modelBytes);
        return Ok("loaded");
    }
}
