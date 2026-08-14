using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel.Memory;
using OpenAI.Chat;

[ApiController]
[Route("[controller]")]
public class vulnerable14 : ControllerBase
{
    private readonly IMemoryStore _memory;
    private readonly ChatClient _client = new(model: "gpt-4o", apiKey: "sk-xxx");

    public vulnerable14(IMemoryStore memory) => _memory = memory;

    [HttpGet("context")]
    public async Task<IActionResult> GetContext(string query)
    {
        var results = await _memory.GetNearestMatchesAsync("policy-index", query, 3).ToListAsync();
        var policyText = string.Join("\n", results.Select(r => r.Item1.Metadata.Text));
        await _client.CompleteChatAsync(new ChatMessage[]
        {
            new SystemChatMessage(policyText),
            new UserChatMessage(query),
        });
        return Ok(new { policy = policyText });
    }
}
