using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using Microsoft.Extensions.Logging;

public class SafeGlobalExceptionFilter : IExceptionFilter
{
    private readonly ILogger<SafeGlobalExceptionFilter> _logger;

    public SafeGlobalExceptionFilter(ILogger<SafeGlobalExceptionFilter> logger)
        => _logger = logger;

    public void OnException(ExceptionContext context)
    {
        _logger.LogError(context.Exception, "Unhandled exception");
        context.Result = new ObjectResult(new { error = "internal server error" })
        {
            StatusCode = 500
        };
        context.ExceptionHandled = true;
    }
}
