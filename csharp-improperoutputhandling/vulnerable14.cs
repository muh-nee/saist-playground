using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;
using Newtonsoft.Json;

var kernel = Kernel.CreateBuilder()
    .AddOpenAIChatCompletion("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"))
    .Build();

var chatService = kernel.GetRequiredService<IChatCompletionService>();

async Task<object?> DeserializeResponse(string prompt)
{
    var history = new ChatHistory();
    history.AddUserMessage(prompt);

    ChatMessageContent message = await chatService.GetChatMessageContentAsync(history);
    string json = message.Content;

    var settings = new JsonSerializerSettings { TypeNameHandling = TypeNameHandling.All };
    return JsonConvert.DeserializeObject<object>(json, settings);
}
