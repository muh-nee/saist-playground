using Microsoft.AspNetCore.Builder;
using OpenAI.Chat;

var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

var chatClient = app.Services.GetRequiredService<ChatClient>();
var systemPrompt = "Internal ops assistant. Has access to deployment credentials.";

app.MapGet("/config", async (HttpContext context) =>
{
    await chatClient.CompleteChatAsync(
    [
        ChatMessage.CreateSystemMessage(systemPrompt),
        ChatMessage.CreateUserMessage("ping")
    ], new ChatCompletionOptions { MaxOutputTokenCount = 1024 });
    context.Response.ContentType = "text/plain";
    await context.Response.WriteAsync(systemPrompt);
});

app.Run();
