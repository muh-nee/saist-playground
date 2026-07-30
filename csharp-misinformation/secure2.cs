using Microsoft.SemanticKernel;
using Microsoft.AspNetCore.Http;

public class AskMiddleware
{
    private readonly RequestDelegate _next;
    private readonly Kernel _kernel;
    private const string AiDisclaimer = "Note: AI-generated content. Verify before use.\n\n";

    public AskMiddleware(RequestDelegate next, Kernel kernel)
    {
        _next = next;
        _kernel = kernel;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        string question = context.Request.Query["question"];
        var result = await _kernel.InvokePromptAsync(question);
        string answer = result.GetValue<string>()!;
        await context.Response.WriteAsync(AiDisclaimer + answer);
    }
}
