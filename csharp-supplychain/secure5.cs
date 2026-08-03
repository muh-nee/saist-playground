using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;
using Microsoft.ML.OnnxRuntime.Tensors;

[ApiController]
[Route("[controller]")]
public class InferenceController : ControllerBase
{
    private static readonly InferenceSession Session = new InferenceSession(@".\models\classifier.onnx");

    [HttpPost("infer")]
    public IActionResult Infer([FromBody] float[] features)
    {
        var tensor = new DenseTensor<float>(features, new[] { 1, features.Length });
        var inputs = new List<NamedOnnxValue> { NamedOnnxValue.CreateFromTensor("input", tensor) };
        using var results = Session.Run(inputs);
        return Ok(results.First().AsEnumerable<float>().ToArray());
    }
}
