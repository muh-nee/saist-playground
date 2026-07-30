using Microsoft.Extensions.AI;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddChatClient(new OpenAI.Chat.ChatClient("gpt-4o", "API_KEY").AsIChatClient());
var app = builder.Build();

app.MapPost("/ask", async (string question, IChatClient chatClient) =>
{
    var result = await chatClient.CompleteAsync(question);
    string answer = result.Message.Text!;
    return TypedResults.Ok(new { answer });
});

app.Run();
