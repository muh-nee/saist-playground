using Ganss.Xss;
using Microsoft.AspNetCore.Http;
using OpenAI.Chat;

var chatClient = new ChatClient("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));
var sanitizer = new HtmlSanitizer();

async Task RenderRichSummary(HttpContext context, string topic)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new UserChatMessage($"Write a brief HTML-formatted summary about: {topic}")
        new ChatCompletionOptions { MaxOutputTokenCount = 1024 }
    );

    string safeHtml = sanitizer.Sanitize(completion.Content[0].Text);

    context.Response.ContentType = "text/html";
    await context.Response.WriteAsync($"<p><em>Note: AI-generated content. Verify independently.</em></p><article>{safeHtml}</article>");
}
