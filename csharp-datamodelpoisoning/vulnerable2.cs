using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;

[ApiController]
public class OnnxController : ControllerBase
{
    record LoadRequest(string ModelPath);

    [HttpPost("load")]
    public IActionResult Load([FromBody] LoadRequest req)
    {
        using var _ = new InferenceSession(req.ModelPath);
        return Ok();
    }
}
