using OpenAI.Chat;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class ChatController : ControllerBase
{
    private readonly ChatClient _chatClient = new ChatClient("gpt-4o", "API_KEY");

    [HttpGet("chat")]
    public async Task<IActionResult> Chat([FromQuery] string q)
    {
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new UserChatMessage(q));
        return Content(completion.Content[0].Text, "text/plain");
    }
}
