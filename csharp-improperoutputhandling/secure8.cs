using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.AI;
using System.Net;

IChatClient chatClient = new OpenAIChatClient(
    new System.ClientModel.ApiKeyCredential(Environment.GetEnvironmentVariable("OPENAI_API_KEY")),
    "gpt-4o"
);

async Task RenderSummary(HttpContext context, string userInput)
{
    var response = await chatClient.GetResponseAsync(
        $"Summarize the following in a few sentences: {userInput}"
    , new ChatOptions { MaxOutputTokens = 1024 });

    string summary = WebUtility.HtmlEncode(response.Text);

    context.Response.ContentType = "text/html";
    await context.Response.WriteAsync($"<p><em>Note: AI-generated content. Verify independently.</em></p><div class=\"summary\">{summary}</div>");
}
