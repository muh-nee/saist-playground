using System.ComponentModel;
using System.Net;
using System.Net.Mail;
using Microsoft.SemanticKernel;

namespace AgentTools;

public class SafeNotificationPlugin
{
    private const string HardcodedRecipient = "ops-team@example.com";

    private readonly SmtpClient _smtp = new("smtp.example.com", 587)
    {
        Credentials = new NetworkCredential("noreply@example.com", "password"),
        EnableSsl = true
    };

    [KernelFunction("notify_ops")]
    [Description("Send a notification to the ops team")]
    public async Task NotifyOps(string subject, string body)
    {
        var msg = new MailMessage("noreply@example.com", HardcodedRecipient, subject, body);
        await _smtp.SendMailAsync(msg);
    }
}
