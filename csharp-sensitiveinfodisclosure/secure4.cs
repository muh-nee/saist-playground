using Microsoft.Extensions.AI;
using System.Net.Http.Headers;
using Microsoft.Extensions.Configuration;

public class SecureSecretForHttpAuthOnly
{
    private IChatClient _chatClient;
    private HttpClient _httpClient;
    private IConfiguration _config;

    public async Task<string> DiagnosePaymentIssue()
    {
        _httpClient.DefaultRequestHeaders.Authorization =
            new AuthenticationHeaderValue("Bearer", _config["stripe:secretKey"]);

        IList<ChatMessage> messages = new List<ChatMessage>
        {
            new ChatMessage(ChatRole.User, "Help diagnose the payment processing issue.")
        };
        ChatResponse chatResponse = await _chatClient.GetResponseAsync(messages);
        return chatResponse.Text;
    }
}
