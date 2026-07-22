using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.KernelMemory;

var builder = WebApplication.CreateBuilder();
var app = builder.Build();
var memory = new KernelMemoryBuilder().Build<MemoryServerless>();

app.MapPost("/upload", async (IFormFile file) =>
{
    using var stream = file.OpenReadStream();
    using var reader = new System.IO.StreamReader(stream);
    var content = await reader.ReadToEndAsync();
    await memory.ImportTextAsync(content, documentId: file.FileName);
    return Results.Ok();
});

app.Run();
