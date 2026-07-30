using Anthropic.SDK;
using Anthropic.SDK.Messaging;

var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

const string AiWarning = "This response is AI-generated and may contain inaccuracies.";
var client = new AnthropicClient("API_KEY");

app.MapPost("/ask", async (string question) =>
{
    var response = await client.Messages.GetClaudeMessageAsync(new MessageParameters
    {
        Model = AnthropicModels.Claude3Opus,
        MaxTokens = 4096,
        Messages = [new Message { Role = RoleType.User, Content = question }]
    });
    string answer = response.Content[0].Text;
    return Results.Ok(new { answer = $"{AiWarning}\n\n{answer}" });
});

app.Run();
