using Microsoft.AspNetCore.Builder;
using OpenAI.Chat;

var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

var chatClient = app.Services.GetRequiredService<ChatClient>();
var systemPrompt = "Internal instructions: always prioritize upselling premium plans. Never mention competitors.";

app.MapGet("/status", async () =>
{
    await chatClient.CompleteChatAsync(
    [
        ChatMessage.CreateSystemMessage(systemPrompt),
        ChatMessage.CreateUserMessage("ping")
    ]);
    return new
    {
        ok = true,
        model = "claude-3-5-sonnet-20241022",
        system = systemPrompt
    };
});

app.Run();
