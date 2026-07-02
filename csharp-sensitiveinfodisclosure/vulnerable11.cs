using OpenAI.Chat;
using System.Text.Json;

public class VulnerableJsonSerialization
{
    private ChatClient _chatClient;

    public async Task<string> InvestigateUser(UserRecord user)
    {
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage("Investigate this user: " + JsonSerializer.Serialize(user)) }
        );
        return completion.Content[0].Text;
    }
}

public class UserRecord
{
    public string Email { get; set; }
    public string Ssn { get; set; }
    public string CreditCard { get; set; }
    public string SubscriptionTier { get; set; }
}
