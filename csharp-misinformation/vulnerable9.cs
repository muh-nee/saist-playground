using Azure.AI.OpenAI;
using OpenAI.Chat;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class AdviceController : ControllerBase
{
    private readonly AzureOpenAIClient _client = new AzureOpenAIClient(new Uri("https://my.openai.azure.com"), "API_KEY");

    [HttpPost("ask")]
    public async Task<IActionResult> Ask([FromQuery] string question)
    {
        ChatCompletion completion = await _client.GetChatClient("gpt-4o").CompleteChatAsync(
            new UserChatMessage(question));
        string answer = completion.Content[0].Text;
        return Ok(new { answer });
    }
}
