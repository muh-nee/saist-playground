using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;

[ApiController]
public class ModelController : ControllerBase
{
    [HttpPost("load")]
    public IActionResult Load()
    {
        var name = Request.Query["model"].ToString();
        var path = Path.Combine("/models", name);
        new MLContext().Model.Load(path, out _);
        return Ok();
    }
}
