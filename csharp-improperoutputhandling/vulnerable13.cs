using System.Diagnostics;
using OpenAI.Chat;

var chatClient = new ChatClient("gpt-4o-mini", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

async Task ProcessFile(string description)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new SystemChatMessage("Output only the filename to process."),
        new UserChatMessage(description)
    );

    string filename = completion.Content[0].Text.Trim();

    var psi = new ProcessStartInfo("converter", filename)
    {
        UseShellExecute = false,
        RedirectStandardOutput = true
    };
    using var process = Process.Start(psi);
    await process!.WaitForExitAsync();
}
