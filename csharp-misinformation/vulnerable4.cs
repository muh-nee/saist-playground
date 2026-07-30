using Anthropic.SDK;
using Anthropic.SDK.Messaging;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class AskController : ControllerBase
{
    private readonly AnthropicClient _client = new AnthropicClient("API_KEY");

    [HttpPost("ask")]
    public async Task<IActionResult> Ask([FromQuery] string question)
    {
        var response = await _client.Messages.GetClaudeMessageAsync(new MessageParameters
        {
            Model = AnthropicModels.Claude3Opus,
            MaxTokens = 4096,
            Messages = [new Message { Role = RoleType.User, Content = question }]
        });
        string answer = response.Content[0].Text;
        return Ok(new { answer });
    }
}
