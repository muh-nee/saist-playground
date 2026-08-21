using Azure.AI.OpenAI;
using Azure;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Diagnostics;

[ApiController]
[Route("[controller]")]
public class DependencyController : ControllerBase
{
    private static readonly HashSet<string> ApprovedPackages = new()
    {
        "Microsoft.ML.OnnxRuntime",
        "Microsoft.ML",
        "TensorFlow.NET",
        "SciSharp.TensorFlow.Redist"
    };

    [HttpPost("install")]
    public async Task<IActionResult> Install([FromBody] string task)
    {
        var client = new OpenAIClient(new Uri("https://example.openai.azure.com/"), new AzureKeyCredential(Environment.GetEnvironmentVariable("AZURE_OPENAI_KEY")));
        var completions = await client.GetChatCompletionsAsync(new ChatCompletionsOptions
        {
            Messages = { new ChatRequestUserMessage($"What NuGet package for: {task}? Reply with only the package name.") },
            DeploymentName = "gpt-4"
        });
        var packageName = completions.Value.Choices[0].Message.Content.Trim();
        if (!ApprovedPackages.Contains(packageName))
            return BadRequest("package not approved");
        Process.Start("dotnet", $"add package {packageName}");
        return Ok($"installed: {packageName}");
    }
}
