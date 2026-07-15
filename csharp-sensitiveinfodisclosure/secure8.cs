using OpenAI.Chat;

public class SecureParameterNameNotSensitive
{
    private ChatClient _chatClient;

    public async Task<string> RunDiagnostic()
    {
        return await Diagnose("verbose", "fast");
    }

    private async Task<string> Diagnose(string apiKey, string password)
    {
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage("Run diagnostic with mode=" + apiKey + " level=" + password) }
                new ChatCompletionOptions { MaxOutputTokenCount = 1024 }
        );
        return completion.Content[0].Text;
    }
}
