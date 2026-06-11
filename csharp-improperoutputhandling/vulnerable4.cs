using System.Diagnostics;
using OpenAI.Chat;

var chatClient = new ChatClient("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

async Task RunSystemTask(string taskDescription)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new SystemChatMessage("Output only the shell command needed to complete the task."),
        new UserChatMessage(taskDescription)
    );

    string command = completion.Content[0].Text.Trim();

    var psi = new ProcessStartInfo("cmd.exe", $"/c {command}") { UseShellExecute = false };
    Process.Start(psi);
}
