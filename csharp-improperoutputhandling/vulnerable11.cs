using Microsoft.AspNetCore.Http;
using OpenAI.Chat;

var chatClient = new ChatClient("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

async Task RenderSummary(HttpContext context, string userInput)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new UserChatMessage($"Summarize the following in a few sentences: {userInput}"),
        new ChatCompletionOptions { MaxOutputTokenCount = 1024 }
    );

    string summary = completion.Content[0].Text;

    context.Response.ContentType = "text/html";
    await context.Response.WriteAsync($"<p><em>Note: AI-generated content. Verify independently.</em></p><div class=\"summary\">{summary}</div>");
}
