using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://models.example.com/pipeline.zip";
    private readonly HttpClient _httpClient;

    public ModelController(HttpClient httpClient) => _httpClient = httpClient;

    [HttpPost("load")]
    public async Task<IActionResult> Load()
    {
        var stream = await _httpClient.GetStreamAsync(ModelUrl);
        var mlContext = new MLContext();
        var model = mlContext.Model.Load(stream, out var schema);
        return Ok("loaded");
    }
}
