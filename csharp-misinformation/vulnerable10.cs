using Microsoft.Extensions.AI;
using Microsoft.AspNetCore.Http;

public class AskMiddleware
{
    private readonly RequestDelegate _next;
    private readonly IChatClient _chatClient;

    public AskMiddleware(RequestDelegate next, IChatClient chatClient)
    {
        _next = next;
        _chatClient = chatClient;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        string question = context.Request.Query["q"];
        var result = await _chatClient.CompleteAsync(question);
        await context.Response.WriteAsync(result.Message.Text!);
    }
}
