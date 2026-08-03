using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;

[ApiController]
[Route("[controller]")]
public class ModelController : ControllerBase
{
    private static readonly string ModelPath = @".\models\pipeline.zip";

    [HttpPost("load")]
    public IActionResult Load()
    {
        var mlContext = new MLContext();
        var model = mlContext.Model.Load(ModelPath, out var schema);
        return Ok("loaded");
    }
}
