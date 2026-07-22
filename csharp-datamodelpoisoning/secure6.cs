using Microsoft.ML;

public class ModelService
{
    private readonly ITransformer _model;

    public ModelService()
    {
        var mlContext = new MLContext();
        _model = mlContext.Model.Load(Environment.GetEnvironmentVariable("ML_MODEL_PATH")!, out _);
    }
}
