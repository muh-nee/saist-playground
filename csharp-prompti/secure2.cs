using Azure.AI.OpenAI;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class SecureAnalysisController : ControllerBase
{
    private const string SystemPrompt = "You are a data analysis assistant. Analyze the provided metrics.";

    private readonly OpenAIClient _client = new OpenAIClient("API_KEY");

    [HttpPost("analyze")]
    public async Task<IActionResult> Analyze(
        [FromQuery] string contextName,
        [FromQuery] string startTime,
        [FromQuery] string endTime,
        [FromBody] string userMessage)
    {
        string userPrompt = $"Context: {contextName}\nTime range: {startTime} to {endTime}";
        if (!string.IsNullOrEmpty(userMessage))
        {
            userPrompt += $"\n\n{userMessage}";
        }

        var chatOptions = new ChatCompletionsOptions
        {
            DeploymentName = "gpt-4o-mini",
            Messages =
            {
                new ChatRequestSystemMessage(SystemPrompt),
                new ChatRequestUserMessage(userPrompt),
            }
        };

        var response = await _client.GetChatCompletionsAsync(chatOptions);
        return Ok(response.Value.Choices[0].Message.Content);
    }
}
