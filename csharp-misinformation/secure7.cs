using Azure.AI.OpenAI;
using OpenAI.Chat;
using Microsoft.Extensions.Hosting;

public class SummaryLoaderService : BackgroundService
{
    private readonly AzureOpenAIClient _client;
    private string _cachedSummary = string.Empty;

    public SummaryLoaderService(AzureOpenAIClient client)
    {
        _client = client;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        ChatCompletion completion = await _client.GetChatClient("gpt-4o-mini").CompleteChatAsync(
            new UserChatMessage("Summarize today's news in one sentence."));
        _cachedSummary = completion.Content[0].Text;
    }
}
