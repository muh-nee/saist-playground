using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;

[ApiController]
[Route("[controller]")]
public class InferenceController : ControllerBase
{
    private static readonly InferenceSession Session = new InferenceSession(@".\models\classifier.onnx");

    [HttpPost("predict")]
    public IActionResult Predict()
    {
        return Ok("predicted");
    }
}
