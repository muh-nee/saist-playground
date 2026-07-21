using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.ML;

[ApiController, Authorize(Roles = "Admin")]
public class AdminModelController : ControllerBase
{
    [HttpPost("admin/load")]
    public IActionResult Load([FromQuery] string modelPath)
    {
        new MLContext().Model.Load(modelPath, out _);
        return Ok();
    }
}
