using Microsoft.Extensions.AI;

public class VulnerableMicrosoftExtensionsAI
{
    private IChatClient _chatClient;

    public async Task<string> DebugWebhook()
    {
        const string webhookSecret = "whsec_abc123xyz789";
        IList<ChatMessage> messages = new List<ChatMessage>
        {
            new ChatMessage(ChatRole.User, "Webhook delivery failed. Secret used: " + webhookSecret)
        };
        ChatResponse chatResponse = await _chatClient.GetResponseAsync(messages);
        return chatResponse.Text;
    }
}
