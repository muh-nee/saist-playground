using Microsoft.EntityFrameworkCore;
using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;
using Microsoft.SemanticKernel.Connectors.OpenAI;

var kernel = Kernel.CreateBuilder()
    .AddOpenAIChatCompletion("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"))
    .Build();

var chatService = kernel.GetRequiredService<IChatCompletionService>();
var context = new AppDbContext();

async Task<List<User>> FindUsers(string userRequest)
{
    var history = new ChatHistory();
    history.AddSystemMessage("Extract only the username from the request. Output plain text only.");
    history.AddUserMessage(userRequest);

    ChatMessageContent message = await chatService.GetChatMessageContentAsync(history, new OpenAIPromptExecutionSettings { MaxTokens = 1024 });
    string username = message.Content.Trim();

    return await context.Users
        .Where(u => u.Name == username)
        .ToListAsync();
}
