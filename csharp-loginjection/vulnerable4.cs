using log4net;
using Microsoft.AspNetCore.Http;

namespace LogInjection.Middleware;

public class RequestValidationMiddleware
{
    private static readonly ILog log = LogManager.GetLogger(typeof(RequestValidationMiddleware));
    private readonly RequestDelegate _next;

    public RequestValidationMiddleware(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        var forwardedFor = context.Request.Headers["X-Forwarded-For"];

        if (!string.IsNullOrEmpty(forwardedFor))
        {
            // VULNERABLE: log4net ILog.Warn with HTTP header value concatenated into the message
            log.Warn("Bad request from: " + forwardedFor);
        }

        await _next(context);
    }
}
