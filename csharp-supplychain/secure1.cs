using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;
using System.Security.Cryptography;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://cdn.example.com/classifier.onnx";
    private static readonly byte[] ExpectedHash = Convert.FromHexString("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
    private readonly HttpClient _httpClient;

    public ModelController(HttpClient httpClient) => _httpClient = httpClient;

    [HttpPost("load")]
    public async Task<IActionResult> Load()
    {
        var modelBytes = await _httpClient.GetByteArrayAsync(ModelUrl);
        using var sha256 = SHA256.Create();
        var hash = sha256.ComputeHash(modelBytes);
        if (!hash.SequenceEqual(ExpectedHash))
            return BadRequest("integrity check failed");
        var session = new InferenceSession(modelBytes);
        return Ok("loaded");
    }
}
