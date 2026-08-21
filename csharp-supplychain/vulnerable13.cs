using Azure.AI.OpenAI;
using Azure;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;

[ApiController]
[Route("[controller]")]
public class SetupController : ControllerBase
{
    [HttpPost("setup")]
    public async Task<IActionResult> Setup([FromBody] string feature)
    {
        var client = new OpenAIClient(new Uri("https://example.openai.azure.com/"), new AzureKeyCredential(Environment.GetEnvironmentVariable("AZURE_OPENAI_KEY")));
        var completions = await client.GetChatCompletionsAsync(new ChatCompletionsOptions
        {
            Messages = { new ChatRequestUserMessage($"List NuGet packages for: {feature}. One package name per line.") },
            DeploymentName = "gpt-4"
        });
        foreach (var line in completions.Value.Choices[0].Message.Content.Split('\n'))
        {
            var pkg = line.Trim();
            if (!string.IsNullOrEmpty(pkg))
                Process.Start(new ProcessStartInfo("dotnet", $"add package {pkg}") { UseShellExecute = false });
        }
        return Ok("done");
    }
}
