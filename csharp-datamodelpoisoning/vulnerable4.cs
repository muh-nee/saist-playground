using Microsoft.AspNetCore.Mvc;
using TorchSharp.Modules;

[ApiController]
public class TorchController : ControllerBase
{
    [HttpPost("load")]
    public IActionResult Load([FromQuery] string modelPath)
    {
        var model = new Linear(10, 1);
        model.load(modelPath);
        return Ok();
    }
}
