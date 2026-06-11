using OpenAI.Chat;
using System.Text.Json;

var chatClient = new ChatClient("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));
var allowedActions = new HashSet<string> { "list", "read" };
var allowedTargets = new HashSet<string> { "logs", "config", "data" };

async Task<string> ExecuteFileOperation(string prompt)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new SystemChatMessage("Return a JSON object with fields: action and target."),
        new UserChatMessage(prompt)
    );

    string json = completion.Content[0].Text;
    var op = JsonSerializer.Deserialize<FileOperation>(json);

    if (op is null || !allowedActions.Contains(op.Action) || !allowedTargets.Contains(op.Target))
        throw new ArgumentException("Disallowed operation.");

    // op.Target is now constrained to one of {"logs", "config", "data"}.
    string path = $"/var/app/{op.Target}.json";
    return await File.ReadAllTextAsync(path);
}

record FileOperation(string Action, string Target);
