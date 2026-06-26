using OpenAI.Chat;

public class VulnerableStringFormat
{
    private ChatClient _chatClient;
    private AppSecrets _secrets;

    public async Task<string> SummarizeRotation()
    {
        string current = _secrets.WebhookSecret;
        string prompt = string.Format("Summarize rotation status. Current webhook secret: {0}", current);
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage(prompt) }
        );
        return completion.Content[0].Text;
    }
}

public class AppSecrets { public string WebhookSecret { get; set; } }
