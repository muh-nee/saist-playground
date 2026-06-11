using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;
using Newtonsoft.Json;

var kernel = Kernel.CreateBuilder()
    .AddOpenAIChatCompletion("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"))
    .Build();

var chatService = kernel.GetRequiredService<IChatCompletionService>();

async Task<FileOperation?> ParseFileOperation(string prompt)
{
    var history = new ChatHistory();
    history.AddSystemMessage("Return a JSON object with fields: action (string) and path (string).");
    history.AddUserMessage(prompt);

    ChatMessageContent message = await chatService.GetChatMessageContentAsync(history);
    string json = message.Content;

    // TypeNameHandling defaults to None — type is fixed to FileOperation, no $type discriminator accepted.
    return JsonConvert.DeserializeObject<FileOperation>(json);
}

record FileOperation(string Action, string Path);
