using Microsoft.ML.OnnxRuntime;

public class ModelLoader
{
    private readonly HttpClient _httpClient;

    public ModelLoader(HttpClient httpClient) => _httpClient = httpClient;

    public async Task<InferenceSession> LoadFromEnv()
    {
        var modelUrl = Environment.GetEnvironmentVariable("MODEL_DOWNLOAD_URL");
        var modelBytes = await _httpClient.GetByteArrayAsync(modelUrl);
        return new InferenceSession(modelBytes);
    }
}
