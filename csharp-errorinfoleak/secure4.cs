using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;

var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

app.MapGet("/data", async (IDataService svc, ILogger<Program> logger) =>
{
    try
    {
        return Results.Ok(await svc.GetData());
    }
    catch (Exception ex)
    {
        logger.LogError(ex, "GetData failed");
        return Results.Problem("internal server error");
    }
});

app.Run();
