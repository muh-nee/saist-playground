using Azure.AI.OpenAI;
using Azure;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;

[ApiController]
[Route("[controller]")]
public class DependencyController : ControllerBase
{
    [HttpPost("install")]
    public async Task<IActionResult> Install([FromBody] string task)
    {
        var client = new OpenAIClient(new Uri("https://example.openai.azure.com/"), new AzureKeyCredential(Environment.GetEnvironmentVariable("AZURE_OPENAI_KEY")));
        var completions = await client.GetChatCompletionsAsync(new ChatCompletionsOptions
        {
            Messages = { new ChatRequestUserMessage($"What NuGet package should I use for: {task}? Reply with only the package name.") },
            DeploymentName = "gpt-4"
        });
        var packageName = completions.Value.Choices[0].Message.Content.Trim();
        Process.Start("dotnet", $"add package {packageName}");
        return Ok($"installing: {packageName}");
    }
}
