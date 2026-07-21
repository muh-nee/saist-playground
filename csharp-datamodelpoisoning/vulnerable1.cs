using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;

[ApiController]
public class ModelController : ControllerBase
{
    [HttpPost("load")]
    public IActionResult Load([FromQuery] string modelPath)
    {
        new MLContext().Model.Load(modelPath, out _);
        return Ok();
    }
}
