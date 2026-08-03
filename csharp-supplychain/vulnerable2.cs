using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private readonly HttpClient _httpClient;

    public ModelController(HttpClient httpClient) => _httpClient = httpClient;

    [HttpPost("load")]
    public async Task<IActionResult> Load([FromBody] string modelUrl)
    {
        var response = await _httpClient.GetAsync(modelUrl);
        var modelBytes = await response.Content.ReadAsByteArrayAsync();
        var session = new InferenceSession(modelBytes);
        return Ok("loaded");
    }
}
