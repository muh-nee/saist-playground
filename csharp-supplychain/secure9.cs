using Microsoft.AspNetCore.Mvc;
using Tensorflow;

[ApiController]
[Route("[controller]")]
public class TfController : ControllerBase
{
    private static readonly string ModelDir = @".\models\saved_model";

    [HttpPost("infer")]
    public IActionResult Infer([FromBody] float[] input)
    {
        var model = tf.saved_model.load(ModelDir);
        return Ok("inferred");
    }
}
