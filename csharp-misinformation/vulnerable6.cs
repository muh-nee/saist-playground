using OpenAI.Chat;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class MedicalController : ControllerBase
{
    private readonly ChatClient _chatClient = new ChatClient("gpt-4o", "API_KEY");

    [HttpGet("advice")]
    public async Task<IActionResult> GetAdvice([FromQuery] string symptom)
    {
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new UserChatMessage("Medical advice for: " + symptom));
        string advice = completion.Content[0].Text;
        return Ok(new { advice });
    }
}
