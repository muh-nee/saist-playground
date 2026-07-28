using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;

[ApiController, Authorize(Roles = "Admin")]
public class AdminTrainingController : ControllerBase
{
    record SentimentData(string Text, bool Label);

    [HttpPost("admin/train")]
    public IActionResult Train([FromBody] IEnumerable<SentimentData> records)
    {
        var mlContext = new MLContext();
        var data = mlContext.Data.LoadFromEnumerable(records);
        mlContext.Transforms.Text.FeaturizeText("Features", "Text").Fit(data);
        return Ok();
    }
}
