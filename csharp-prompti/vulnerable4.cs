using Azure.AI.OpenAI;
using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel.Memory;

[ApiController]
[Route("[controller]")]
public class MemoryController : ControllerBase
{
    private readonly OpenAIClient _client;
    private readonly ISemanticTextMemory _memory;

    public MemoryController(OpenAIClient client, ISemanticTextMemory memory)
    {
        _client = client;
        _memory = memory;
    }

    [HttpPost("summarize")]
    public async Task<IActionResult> SummarizeAndStore([FromQuery] string userQuery, [FromQuery] string sessionId)
    {
        var options = new ChatCompletionsOptions
        {
            Messages = { new ChatMessage(ChatRole.User, userQuery) },
            MaxTokens = 512
        };
        var response = await _client.GetChatCompletionsAsync("gpt-4o", options);
        var llmOutput = response.Value.Choices[0].Message.Content;
        await _memory.SaveInformationAsync("session_memory", id: sessionId, text: llmOutput);
        return Ok(new { stored = true });
    }
}
