using Microsoft.AspNetCore.Http;
using Serilog;

namespace LogInjection.Middleware;

public class SearchMiddleware
{
    private readonly RequestDelegate _next;

    public SearchMiddleware(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        var q = context.Request.Query["q"];

        // VULNERABLE: query string value is embedded in the message via string interpolation;
        // Serilog treats the whole interpolated string as an opaque message, not a structured template
        Log.Information($"Search query: {q}");

        await _next(context);
    }
}
