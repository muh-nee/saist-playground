using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel.Memory;
using OpenAI.Chat;

[ApiController]
[Route("[controller]")]
public class secure9 : ControllerBase
{
    private static readonly ChatTool[] tools = [
        ChatTool.CreateFunctionTool("get_data", "Internal data retrieval.")
    ];

    private readonly IMemoryStore _memory;
    private readonly ChatClient _client = new(model: "gpt-4o", apiKey: "sk-xxx");

    public secure9(IMemoryStore memory) => _memory = memory;

    [HttpPost("chat")]
    public async Task<IActionResult> Chat(string userMessage)
    {
        var results = await _memory.GetNearestMatchesAsync("policy-index", userMessage, 3).ToListAsync();
        var policyText = string.Join("\n", results.Select(r => r.Item1.Metadata.Text));
        var response = await _client.CompleteChatAsync(new ChatMessage[]
        {
            new SystemChatMessage(policyText),
            new UserChatMessage(userMessage),
        }, new ChatCompletionOptions { Tools = { tools[0] } });
        return Ok(new { reply = response.Value.Content[0].Text });
    }
}
