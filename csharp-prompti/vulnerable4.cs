using Azure.AI.OpenAI;
using Microsoft.SemanticKernel.Memory;

public class MemoryService
{
    private readonly OpenAIClient _client;
    private readonly ISemanticTextMemory _memory;

    public MemoryService(OpenAIClient client, ISemanticTextMemory memory)
    {
        _client = client;
        _memory = memory;
    }

    public async Task SummarizeAndStoreAsync(string userQuery, string sessionId)
    {
        var options = new ChatCompletionsOptions
        {
            Messages = { new ChatMessage(ChatRole.User, userQuery) },
            MaxTokens = 512
        };
        var response = await _client.GetChatCompletionsAsync("gpt-4o", options);
        var llmOutput = response.Value.Choices[0].Message.Content;
        await _memory.SaveInformationAsync("session_memory", id: sessionId, text: llmOutput);
    }
}
