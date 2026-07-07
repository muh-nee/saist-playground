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

        // SAFE: Serilog named placeholder — {Query} is a structured property captured separately,
        // not embedded into the message string via concatenation or interpolation
        Log.Information("search_request for {Query}", q);

        await _next(context);
    }
}
