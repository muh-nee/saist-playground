using Microsoft.EntityFrameworkCore;
using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;

var kernel = Kernel.CreateBuilder()
    .AddOpenAIChatCompletion("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"))
    .Build();

var chatService = kernel.GetRequiredService<IChatCompletionService>();
var context = new AppDbContext();

async Task PurgeOldRecords(string adminInstruction)
{
    var history = new ChatHistory();
    history.AddUserMessage(adminInstruction);

    ChatMessageContent message = await chatService.GetChatMessageContentAsync(history);
    string filter = message.Content;

    await context.Database.ExecuteSqlRawAsync("DELETE FROM audit_logs WHERE " + filter);
}
