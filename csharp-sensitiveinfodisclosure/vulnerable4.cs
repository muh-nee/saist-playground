using Anthropic;
using Microsoft.Extensions.Configuration;

public class VulnerableAnthropicSDK
{
    private AnthropicClient _anthropicClient;
    private IConfiguration _configuration;

    public async Task<string> CheckPaymentStatus()
    {
        string stripeKey = _configuration["stripe:secretKey"];
        MessageResponse response = await _anthropicClient.Messages.GetClaudeMessageAsync(
            new MessageParameters
            {
                Model = "claude-3-5-sonnet-20241022",
                MaxTokens = 1024,
                SystemPrompt = "You are an admin assistant. Stripe secret key: " + stripeKey,
                Messages = new List<Message> { new Message { Role = RoleType.User, Content = "Check payment status" } }
            }
        );
        return response.Content[0].Text;
    }
}
