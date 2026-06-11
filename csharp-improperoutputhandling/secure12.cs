using System.Diagnostics;
using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;

var kernel = Kernel.CreateBuilder()
    .AddOpenAIChatCompletion("gpt-4o-mini", Environment.GetEnvironmentVariable("OPENAI_API_KEY"))
    .Build();

var chatService = kernel.GetRequiredService<IChatCompletionService>();
var allowedFiles = new HashSet<string> { "report.pdf", "summary.txt", "data.csv" };

async Task ConvertDocument(string description)
{
    var history = new ChatHistory();
    history.AddSystemMessage("Output only the filename to convert from the description.");
    history.AddUserMessage(description);

    ChatMessageContent message = await chatService.GetChatMessageContentAsync(history);
    string filename = message.Content.Trim();

    if (!allowedFiles.Contains(filename))
        throw new ArgumentException($"File not allowed: {filename}");

    // LLM value is passed as a data argument to a fixed command — not as a script.
    var psi = new ProcessStartInfo("converter", filename)
    {
        UseShellExecute = false,
        RedirectStandardOutput = true
    };
    using var process = Process.Start(psi);
    await process!.WaitForExitAsync();
}
