using Microsoft.ML.OnnxRuntime;
using System.Net;
using System.Security;
using System.Security.Cryptography;

public class ModelLoader
{
    private static readonly string ModelUrl = "https://models.example.com/classifier.onnx";
    private static readonly byte[] ExpectedHash = Convert.FromHexString("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");

    public InferenceSession Load()
    {
        var localPath = Path.Combine(Path.GetTempPath(), "model.onnx");
        new WebClient().DownloadFile(ModelUrl, localPath);
        var fileBytes = File.ReadAllBytes(localPath);
        using var sha256 = SHA256.Create();
        if (!sha256.ComputeHash(fileBytes).SequenceEqual(ExpectedHash))
            throw new SecurityException("integrity check failed");
        return new InferenceSession(localPath);
    }
}
