using Microsoft.AspNetCore.Http;
using OpenAI.Chat;

var chatClient = new ChatClient("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

async Task RenderSummary(HttpContext context, string userInput)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new UserChatMessage($"Summarize the following in a few sentences: {userInput}")
    );

    string summary = completion.Content[0].Text;

    context.Response.ContentType = "text/html";
    await context.Response.WriteAsync($"<div class=\"summary\">{summary}</div>");
}
