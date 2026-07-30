using Azure.AI.OpenAI;
using OpenAI.Chat;

var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

var client = new AzureOpenAIClient(new Uri("https://my.openai.azure.com"), "API_KEY");

app.MapPost("/ask", async (string question) =>
{
    ChatCompletion completion = await client.GetChatClient("gpt-4o").CompleteChatAsync(
        new UserChatMessage(question));
    string answer = completion.Content[0].Text;
    return Results.Ok(new { answer });
});

app.Run();
