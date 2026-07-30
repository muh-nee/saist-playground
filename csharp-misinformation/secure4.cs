using Anthropic.SDK;
using Anthropic.SDK.Messaging;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Concurrent;

[ApiController]
[Route("[controller]")]
public class ReviewController : ControllerBase
{
    private readonly AnthropicClient _client = new AnthropicClient("API_KEY");
    private static readonly ConcurrentDictionary<string, string> Pending = new();

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
        Pending["pending-1"] = answer;
        return Ok(new { status = "pending_review", id = "pending-1" });
    }
}
