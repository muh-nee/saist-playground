using Microsoft.AspNetCore.Mvc;
using System.IO.Compression;
using Tensorflow;

[ApiController]
[Route("[controller]")]
public class TfModelController : ControllerBase
{
    private static readonly string ModelUrl = "https://storage.example.com/saved_model.zip";
    private readonly HttpClient _httpClient;

    public TfModelController(HttpClient httpClient) => _httpClient = httpClient;

    [HttpPost("load")]
    public async Task<IActionResult> Load()
    {
        var archiveBytes = await _httpClient.GetByteArrayAsync(ModelUrl);
        var zipPath = Path.Combine(Path.GetTempPath(), "model.zip");
        var exportDir = Path.Combine(Path.GetTempPath(), "saved_model");
        File.WriteAllBytes(zipPath, archiveBytes);
        ZipFile.ExtractToDirectory(zipPath, exportDir);
        var model = tf.saved_model.load(exportDir);
        return Ok("loaded");
    }
}
