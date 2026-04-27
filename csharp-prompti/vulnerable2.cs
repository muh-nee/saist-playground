using Azure.AI.OpenAI;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class TranslationController : ControllerBase
{
    private readonly OpenAIClient _client = new OpenAIClient("API_KEY");

    [HttpPost("translate")]
    public async Task<IActionResult> Translate(
        [FromQuery] string targetLanguage,
        [FromQuery] string tone,
        [FromBody] string text)
    {
        string systemPrompt = "You are a translation assistant. " +
            "Translate to: " + targetLanguage + ". " +
            "Use tone: " + tone + ".";

        var chatOptions = new ChatCompletionsOptions
        {
            DeploymentName = "gpt-4o-mini",
            Messages =
            {
                new ChatRequestSystemMessage(systemPrompt),
                new ChatRequestUserMessage(text),
            }
        };

        var response = await _client.GetChatCompletionsAsync(chatOptions);
        return Ok(response.Value.Choices[0].Message.Content);
    }
}
