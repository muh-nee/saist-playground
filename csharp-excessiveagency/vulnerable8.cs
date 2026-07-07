using System.ComponentModel;
using System.Net;
using System.Net.Mail;
using Microsoft.SemanticKernel;

namespace AgentTools;

public class EmailPlugin
{
    private readonly SmtpClient _smtp = new("smtp.example.com", 587)
    {
        Credentials = new NetworkCredential("noreply@example.com", "password"),
        EnableSsl = true
    };

    [KernelFunction("send_email")]
    [Description("Send an email to the specified recipient")]
    public async Task SendEmail(string to, string subject, string body)
    {
        var msg = new MailMessage("noreply@example.com", to, subject, body);
        await _smtp.SendMailAsync(msg);
    }
}
