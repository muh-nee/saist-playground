using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;
using System.Security.Cryptography;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://models.example.com/resnet50.onnx";
    private const string ExpectedHex = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    private readonly HttpClient _httpClient;

    public ModelController(HttpClient httpClient) => _httpClient = httpClient;

    [HttpPost("load")]
    public async Task<IActionResult> Load()
    {
        var modelBytes = await _httpClient.GetByteArrayAsync(ModelUrl);
        if (Convert.ToHexString(SHA256.HashData(modelBytes)) != ExpectedHex)
            return BadRequest("integrity check failed");
        var session = new InferenceSession(modelBytes);
        return Ok("loaded");
    }
}
