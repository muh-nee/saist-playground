using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;

[ApiController]
public class OnnxUploadController : ControllerBase
{
    [HttpPost("upload")]
    public async Task<IActionResult> Upload(IFormFile modelFile)
    {
        using var ms = new MemoryStream();
        await modelFile.CopyToAsync(ms);
        using var _ = new InferenceSession(ms.ToArray());
        return Ok();
    }
}
