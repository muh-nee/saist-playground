using Microsoft.Extensions.DependencyInjection;
using Microsoft.ML.OnnxRuntime;

public class ModelService
{
    private readonly InferenceSession _session;

    public ModelService()
    {
        _session = new InferenceSession(@".\models\production_model.onnx");
    }
}
