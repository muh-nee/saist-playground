using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;
using System.Net.Http.Json;

[ApiController]
public class TrainingController : ControllerBase
{
    [HttpPost("train-from-url")]
    public async Task<IActionResult> TrainFromUrl([FromBody] TrainRequest req)
    {
        using var client = new HttpClient();
        var records = await client.GetFromJsonAsync<IEnumerable<SentimentData>>(req.DatasetUrl);
        var mlContext = new MLContext();
        var data = mlContext.Data.LoadFromEnumerable(records);
        mlContext.Transforms.Text.FeaturizeText("Features", "Text").Fit(data);
        return Ok();
    }
}

public record TrainRequest(string DatasetUrl);
public record SentimentData(string Text, bool Label);
