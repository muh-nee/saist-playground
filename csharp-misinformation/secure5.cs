using OpenAI.Chat;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class ClassifyController : ControllerBase
{
    private readonly ChatClient _chatClient = new ChatClient("gpt-4o-mini", "API_KEY");

    [HttpPost("classify")]
    public async Task<IActionResult> Classify([FromQuery] string text)
    {
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new UserChatMessage($"Classify as positive or negative: {text}"));
        string label = completion.Content[0].Text.Trim().ToLower();
        bool isPositive = label == "positive";
        return Ok(new { is_positive = isPositive });
    }
}
