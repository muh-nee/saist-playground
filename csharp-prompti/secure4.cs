using Azure.AI.OpenAI;

public class SafeAgentService
{
    private const string SystemPrompt = "You are a helpful search assistant.";

    private readonly OpenAIClient _client;

    public SafeAgentService(OpenAIClient client)
    {
        _client = client;
    }

    public async Task<string> AgentTurnAsync(List<ChatMessage> messages, Dictionary<string, object> toolResult)
    {
        if (toolResult["result_count"] is not int resultCount)
            throw new ArgumentException("Unexpected MCP tool output format");

        var safeContent = $"Found {resultCount} results";
        messages.Add(new ChatMessage(ChatRole.User, safeContent));

        var options = new ChatCompletionsOptions { MaxTokens = 512 };
        options.Messages.Add(new ChatMessage(ChatRole.System, SystemPrompt));
        foreach (var msg in messages) options.Messages.Add(msg);

        var response = await _client.GetChatCompletionsAsync("gpt-4o", options);
        return response.Value.Choices[0].Message.Content;
    }
}
