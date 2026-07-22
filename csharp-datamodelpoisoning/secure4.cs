using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;

[ApiController]
public class OnnxController : ControllerBase
{
    private const string AllowedDir = "/opt/models";

    [HttpPost("load")]
    public IActionResult Load([FromQuery] string modelName)
    {
        var path = Path.GetFullPath(Path.Combine(AllowedDir, modelName));
        if (!path.StartsWith(AllowedDir + Path.DirectorySeparatorChar)) return BadRequest();
        using var _ = new InferenceSession(path);
        return Ok();
    }
}
