using Azure.AI.OpenAI;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class ChatController : ControllerBase
{
    private readonly OpenAIClient _client = new OpenAIClient("API_KEY");

    [HttpPost("chat")]
    public async Task<IActionResult> Chat([FromQuery] string userRole, [FromBody] string message)
    {
        var chatOptions = new ChatCompletionsOptions
        {
            DeploymentName = "gpt-4o-mini",
            Messages =
            {
                new ChatRequestSystemMessage($"You are a {userRole} assistant."),
                new ChatRequestUserMessage(message),
            }
        };

        var response = await _client.GetChatCompletionsAsync(chatOptions);
        return Ok(response.Value.Choices[0].Message.Content);
    }
}
