using Azure.AI.OpenAI;
using ModelContextProtocol.Client;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class AgentController : ControllerBase
{
    private readonly OpenAIClient _client;
    private readonly IMcpClient _mcpClient;

    public AgentController(OpenAIClient client, IMcpClient mcpClient)
    {
        _client = client;
        _mcpClient = mcpClient;
    }

    [HttpPost("agent")]
    public async Task<IActionResult> AgentTurn([FromQuery] string userQuery, [FromBody] List<ChatMessage> messages)
    {
        var toolResult = await _mcpClient.CallToolAsync("web_search", new { query = userQuery });
        var toolOutput = toolResult.Content[0].Text;

        messages.Add(new ChatMessage(ChatRole.User, toolOutput));

        var options = new ChatCompletionsOptions { MaxTokens = 512 };
        foreach (var msg in messages) options.Messages.Add(msg);

        var response = await _client.GetChatCompletionsAsync("gpt-4o", options);
        return Ok(new { reply = response.Value.Choices[0].Message.Content });
    }
}
