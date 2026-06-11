using OpenAI.Chat;

var chatClient = new ChatClient("gpt-4o-mini", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

async Task<string> OpenUserDocument(string description)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new SystemChatMessage("Output only the filename that best matches the description."),
        new UserChatMessage(description)
    );

    string filename = completion.Content[0].Text.Trim();
    string fullPath = Path.Combine("/var/app/docs", filename);
    return await File.ReadAllTextAsync(fullPath);
}
