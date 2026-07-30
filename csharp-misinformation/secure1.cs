using OpenAI.Chat;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class AskController : ControllerBase
{
    private const string Disclaimer = "AI-generated content. Please verify independently before acting on this information.";
    private readonly ChatClient _chatClient = new ChatClient("gpt-4o", "API_KEY");

    [HttpPost("ask")]
    public async Task<IActionResult> Ask([FromQuery] string question)
    {
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new UserChatMessage(question));
        string answer = completion.Content[0].Text;
        return Ok(new { answer, disclaimer = Disclaimer });
    }
}
