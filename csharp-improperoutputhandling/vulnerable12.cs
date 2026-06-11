using Anthropic.SDK;
using Anthropic.SDK.Messaging;
using System.Net.Mail;

var anthropicClient = new AnthropicClient(Environment.GetEnvironmentVariable("ANTHROPIC_API_KEY"));
var smtp = new SmtpClient("smtp.example.com");

async Task SendDigestEmail(string topic, string recipient)
{
    var parameters = new MessageParameters
    {
        Model = AnthropicModels.Claude3Haiku,
        MaxTokens = 256,
        Messages = new List<Message>
        {
            new Message(RoleType.User, $"Write a concise email subject line about: {topic}")
        }
    };

    var response = await anthropicClient.Messages.GetClaudeMessageAsync(parameters);
    string subject = response.Content[0].Text;

    var mail = new MailMessage("noreply@example.com", recipient)
    {
        Subject = subject,
        Body = "Daily digest attached."
    };
    smtp.Send(mail);
}
