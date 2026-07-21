using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;

[ApiController]
public class ModelController : ControllerBase
{
    private static readonly HashSet<string> _approved = ["classifier-v1.zip", "classifier-v2.zip"];

    [HttpPost("load")]
    public IActionResult Load([FromQuery] string modelName)
    {
        if (!_approved.Contains(modelName)) return Forbid();
        new MLContext().Model.Load(Path.Combine("./models", modelName), out _);
        return Ok();
    }
}
