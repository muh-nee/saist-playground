using Microsoft.ML.OnnxRuntime;
using System.Security;
using System.Security.Cryptography;

public class ModelLoader
{
    private static readonly string ModelUrl = "https://cdn.example.com/model.onnx";
    private static readonly byte[] ExpectedHash = Convert.FromHexString("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
    private readonly HttpClient _httpClient;

    public ModelLoader(HttpClient httpClient) => _httpClient = httpClient;

    public async Task<InferenceSession> Load()
    {
        var modelBytes = await _httpClient.GetByteArrayAsync(ModelUrl);
        using var sha256 = SHA256.Create();
        if (!sha256.ComputeHash(modelBytes).SequenceEqual(ExpectedHash))
            throw new SecurityException("integrity check failed");
        return new InferenceSession(modelBytes);
    }
}
