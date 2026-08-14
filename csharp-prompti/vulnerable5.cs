using Azure.AI.OpenAI;

public class AgentService
{
    private readonly OpenAIClient _client;

    public AgentService(OpenAIClient client)
    {
        _client = client;
    }

    public async Task<string> AgentTurnAsync(List<ChatMessage> messages, Dictionary<string, object> toolResult)
    {
        var toolOutput = (string)toolResult["output"];
        messages.Add(new ChatMessage(ChatRole.User, toolOutput));

        var options = new ChatCompletionsOptions { MaxTokens = 512 };
        foreach (var msg in messages) options.Messages.Add(msg);

        var response = await _client.GetChatCompletionsAsync("gpt-4o", options);
        return response.Value.Choices[0].Message.Content;
    }
}
