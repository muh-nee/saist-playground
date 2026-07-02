using Amazon.BedrockRuntime;
using Amazon.BedrockRuntime.Model;
using System.Text;
using System.Text.Json;

public class VulnerableBedrockInvokeModel
{
    private AmazonBedrockRuntimeClient _bedrockRuntimeClient;

    public async Task<string> RotateSecret()
    {
        string dbPassword = Environment.GetEnvironmentVariable("DB_PASSWORD");
        var body = JsonSerializer.Serialize(new
        {
            messages = new[] { new { role = "user", content = "Rotate this password: " + dbPassword } }
        });
        var invokeRequest = new InvokeModelRequest
        {
            ModelId = "anthropic.claude-3-5-sonnet-20241022-v2:0",
            Body = new MemoryStream(Encoding.UTF8.GetBytes(body))
        };
        var invokeResponse = await _bedrockRuntimeClient.InvokeModelAsync(invokeRequest);
        return new StreamReader(invokeResponse.Body).ReadToEnd();
    }
}
