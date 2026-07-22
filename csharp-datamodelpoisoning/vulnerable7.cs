using Microsoft.ML.OnnxRuntime;

var app = WebApplication.Create(args);
app.MapPost("/load", (string modelPath) =>
{
    using var _ = new InferenceSession(modelPath);
    return Results.Ok();
});
app.Run();
