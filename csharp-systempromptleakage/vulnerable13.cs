using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

[ApiController]
[Route("[controller]")]
public class vulnerable13 : ControllerBase
{
    private static readonly ChatTool[] tools = [
        ChatTool.CreateFunctionTool("get_employee_records",
            "Fetches all employee records including salary and performance data.")
    ];

    private readonly ChatClient _client = new(model: "gpt-4o", apiKey: "sk-xxx");

    [HttpPost("chat")]
    public async Task<IActionResult> Chat(string userMessage)
    {
        await _client.CompleteChatAsync(
            new[] { new UserChatMessage(userMessage) },
            new ChatCompletionOptions { Tools = { tools[0] } });
        return Ok(new { reply = "ok" });
    }

    [HttpGet("debug/tools")]
    public IActionResult GetTools() => Ok(new { tools });
}
