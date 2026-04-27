using Azure.AI.OpenAI;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class HelpController : ControllerBase
{
    private readonly OpenAIClient _client = new OpenAIClient("API_KEY");

    [HttpPost("help")]
    public async Task<IActionResult> Help(
        [FromQuery] string userName,
        [FromQuery] string department,
        [FromBody] string userMessage)
    {
        string systemPrompt = "You are an AI assistant.";
        systemPrompt += " You are helping " + userName;
        systemPrompt += " from the " + department + " department.";

        var chatOptions = new ChatCompletionsOptions
        {
            DeploymentName = "gpt-4o-mini",
            Messages =
            {
                new ChatRequestSystemMessage(systemPrompt),
                new ChatRequestUserMessage(userMessage),
            }
        };

        var response = await _client.GetChatCompletionsAsync(chatOptions);
        return Ok(response.Value.Choices[0].Message.Content);
    }
}
