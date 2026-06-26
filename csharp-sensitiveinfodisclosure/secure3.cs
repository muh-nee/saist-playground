using OpenAI.Chat;
using System.Security.Cryptography;
using System.Text;

public class SecurePIIHashed
{
    private ChatClient _chatClient;

    public async Task<string> AnalyzeUser(string email)
    {
        using var sha256 = SHA256.Create();
        string emailHash = Convert.ToBase64String(sha256.ComputeHash(Encoding.UTF8.GetBytes(email)));
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage("Analyze activity for user hash " + emailHash) }
        );
        return completion.Content[0].Text;
    }
}
