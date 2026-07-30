using Anthropic.SDK;
using Anthropic.SDK.Messaging;

var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

var client = new AnthropicClient("API_KEY");

app.MapPost("/invest", async (string question) =>
{
    var response = await client.Messages.GetClaudeMessageAsync(new MessageParameters
    {
        Model = AnthropicModels.Claude3Opus,
        MaxTokens = 4096,
        Messages = [new Message { Role = RoleType.User, Content = question }]
    });
    string advice = response.Content[0].Text;
    return Results.Ok(new { advice });
});

app.Run();
