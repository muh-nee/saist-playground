using System.Text.RegularExpressions;
using Azure.AI.OpenAI;
using Microsoft.SemanticKernel.Memory;

public class SafeMemoryService
{
    private static readonly Regex InjectionRe = new(
        @"(ignore (all |previous )?instructions?|you are now|system:)",
        RegexOptions.IgnoreCase | RegexOptions.Compiled);
    private static readonly Regex ControlRe = new(@"<\|[^|]*\|>", RegexOptions.Compiled);

    private readonly OpenAIClient _client;
    private readonly ISemanticTextMemory _memory;

    public SafeMemoryService(OpenAIClient client, ISemanticTextMemory memory)
    {
        _client = client;
        _memory = memory;
    }

    private static string Sanitize(string text)
    {
        text = InjectionRe.Replace(text, "");
        text = ControlRe.Replace(text, "");
        return text.Trim();
    }

    public async Task SummarizeAndStoreAsync(string userQuery, string sessionId)
    {
        var options = new ChatCompletionsOptions
        {
            Messages = { new ChatMessage(ChatRole.User, userQuery) },
            MaxTokens = 512
        };
        var response = await _client.GetChatCompletionsAsync("gpt-4o", options);
        var sanitized = Sanitize(response.Value.Choices[0].Message.Content);
        await _memory.SaveInformationAsync("session_memory", id: sessionId, text: sanitized);
    }
}
