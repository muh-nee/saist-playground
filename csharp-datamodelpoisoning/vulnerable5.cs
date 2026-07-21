using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;

[ApiController]
public class TrainingController : ControllerBase
{
    record SentimentData(string Text, bool Label);

    [HttpPost("train")]
    public IActionResult Train([FromQuery] string dataPath)
    {
        var mlContext = new MLContext();
        var data = mlContext.Data.LoadFromTextFile<SentimentData>(dataPath, separatorChar: ',');
        mlContext.Transforms.Text.FeaturizeText("Features", "Text").Fit(data);
        return Ok();
    }
}
