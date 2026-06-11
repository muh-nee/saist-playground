using Anthropic.SDK;
using Anthropic.SDK.Messaging;
using RazorEngine;
using RazorEngine.Templating;

var anthropicClient = new AnthropicClient(Environment.GetEnvironmentVariable("ANTHROPIC_API_KEY"));

async Task<string> RenderEmailTemplate(string templateRequest, object model)
{
    var parameters = new MessageParameters
    {
        Model = AnthropicModels.Claude3Haiku,
        MaxTokens = 512,
        Messages = new List<Message>
        {
            new Message(RoleType.User, templateRequest)
        }
    };

    var response = await anthropicClient.Messages.GetClaudeMessageAsync(parameters);
    string template = response.Content[0].Text;

    return Engine.Razor.RunCompile(template, "emailTemplate", null, model);
}
