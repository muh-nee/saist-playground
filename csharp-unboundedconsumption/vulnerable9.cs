using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

[ApiController]
[Route("[controller]")]
public class vulnerable9 : ControllerBase
{
    private readonly ChatClient _client = new ChatClient(
        "gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

    [HttpPost("draft")]
    public async Task<IActionResult> Draft([FromBody] string topic)
    {
        var result = await _client.CompleteChatAsync(
            [ChatMessage.CreateUserMessage($"Draft content about {topic}")]);
        return Ok(result.Value.Content[0].Text);
    }

    [HttpPost("review")]
    public async Task<IActionResult> Review([FromBody] string draft)
    {
        var result = await _client.CompleteChatAsync(
            [ChatMessage.CreateUserMessage($"Review this: {draft}")]);
        return Ok(result.Value.Content[0].Text);
    }
}
