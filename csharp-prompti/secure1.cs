using Azure.AI.OpenAI;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class SecureChatController : ControllerBase
{
    private const string SystemPrompt = "You are a helpful customer support assistant.";

    private readonly OpenAIClient _client = new OpenAIClient("API_KEY");

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromBody] string message)
    {
        var chatOptions = new ChatCompletionsOptions
        {
            DeploymentName = "gpt-4o-mini",
            Messages =
            {
                new ChatRequestSystemMessage(SystemPrompt),
                new ChatRequestUserMessage(message),
            }
        };

        var response = await _client.GetChatCompletionsAsync(chatOptions);
        return Ok(response.Value.Choices[0].Message.Content);
    }
}
