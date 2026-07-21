using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;
using Microsoft.ML.Data;

[ApiController]
public class PredictionController : ControllerBase
{
    record SentimentData(string Text);

    [ColumnName("PredictedLabel")]
    record SentimentPrediction(bool Prediction);

    private readonly PredictionEngine<SentimentData, SentimentPrediction> _engine = BuildEngine();

    [HttpPost("predict")]
    public IActionResult Predict([FromBody] SentimentData input)
    {
        var prediction = _engine.Predict(input);
        return Ok(prediction.Prediction);
    }

    private static PredictionEngine<SentimentData, SentimentPrediction> BuildEngine() => null!;
}
