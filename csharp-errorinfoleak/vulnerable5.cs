using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;

var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

app.MapGet("/data", async (IDataService svc) =>
{
    try
    {
        return Results.Ok(await svc.GetData());
    }
    catch (Exception ex)
    {
        return Results.Problem(detail: ex.Message, statusCode: 500);
    }
});

app.Run();

public interface IDataService
{
    Task<object> GetData();
}
