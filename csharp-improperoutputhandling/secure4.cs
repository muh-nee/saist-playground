using Microsoft.EntityFrameworkCore;
using OpenAI.Chat;

var chatClient = new ChatClient("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));
var context = new AppDbContext();

async Task ArchiveOldLogs(string adminInstruction)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new SystemChatMessage("Extract only the category name from the instruction. Output plain text only."),
        new UserChatMessage(adminInstruction)
    );

    string category = completion.Content[0].Text.Trim();

    await context.Database.ExecuteSqlInterpolatedAsync(
        $"DELETE FROM audit_logs WHERE category = {category} AND created_at < DATEADD(year, -1, GETDATE())"
    );
}
