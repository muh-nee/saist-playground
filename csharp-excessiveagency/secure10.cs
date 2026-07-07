using System.ComponentModel;
using Microsoft.Extensions.Logging;
using Microsoft.SemanticKernel;

namespace AgentTools;

public class AuditPlugin
{
    private readonly ILogger<AuditPlugin> _logger;

    public AuditPlugin(ILogger<AuditPlugin> logger)
    {
        _logger = logger;
    }

    [KernelFunction("log_event")]
    [Description("Record an audit event in the application log")]
    public void LogEvent(string eventName, string details)
    {
        _logger.LogInformation("Audit: {EventName} — {Details}", eventName, details);
    }
}
