using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.KernelMemory;

var builder = WebApplication.CreateBuilder();
var app = builder.Build();
var memory = new KernelMemoryBuilder().Build<MemoryServerless>();

app.MapPost("/ingest", async (IngestRequest request) =>
{
    await memory.ImportTextAsync(request.Text, documentId: Guid.NewGuid().ToString());
    return Results.Ok();
});

app.Run();

record IngestRequest(string Text);
