using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;
using System.Security.Cryptography;

[ApiController]
public class TrainingController : ControllerBase
{
    private const string ExpectedSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    private const string TrustedDatasetUrl = "https://internal.example.com/datasets/approved.json";

    [HttpPost("train-verified")]
    public async Task<IActionResult> TrainVerified()
    {
        using var client = new HttpClient();
        var bytes = await client.GetByteArrayAsync(TrustedDatasetUrl);
        var actual = Convert.ToHexString(SHA256.HashData(bytes)).ToLowerInvariant();
        if (actual != ExpectedSha256) return StatusCode(403, "integrity check failed");
        var records = System.Text.Json.JsonSerializer.Deserialize<IEnumerable<SentimentData>>(bytes)!;
        var mlContext = new MLContext();
        var data = mlContext.Data.LoadFromEnumerable(records);
        mlContext.Transforms.Text.FeaturizeText("Features", "Text").Fit(data);
        return Ok();
    }
}

public record SentimentData(string Text, bool Label);
