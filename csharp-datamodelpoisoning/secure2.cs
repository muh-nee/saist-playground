using Microsoft.AspNetCore.Mvc;
using Microsoft.ML.OnnxRuntime;
using Microsoft.ML.OnnxRuntime.Tensors;

[ApiController]
public class InferenceController : ControllerBase
{
    private readonly InferenceSession _session = new("./models/classifier.onnx");

    [HttpPost("infer")]
    public IActionResult Infer([FromBody] float[] features)
    {
        var tensor = new DenseTensor<float>(features, new[] { 1, features.Length });
        var result = _session.Run(new[] { NamedOnnxValue.CreateFromTensor("input", tensor) });
        return Ok(result.First().AsEnumerable<float>().ToArray());
    }
}
