using NLog;

namespace ChatApi;

public class NLogRequestHandler
{
    private static readonly Logger Logger = LogManager.GetCurrentClassLogger();
    private readonly string _systemPrompt = LoadConfig().SystemPrompt;

    public async Task<string> HandleRequestAsync(string userMessage)
    {
        Logger.Info("Using system prompt: {0}", _systemPrompt);
        return await CallLlmAsync(_systemPrompt, userMessage);
    }

    private static async Task<string> CallLlmAsync(string systemPrompt, string userMessage) =>
        await Task.FromResult("response");

    private static (string SystemPrompt, string Model) LoadConfig() =>
        ("Internal ops assistant. Has access to deployment credentials and CI secrets.", "gpt-4o");
}
