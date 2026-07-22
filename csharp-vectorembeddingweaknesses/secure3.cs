using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.AI;
using System.Collections.Generic;

[ApiController]
[Route("api/[controller]")]
public class TenantIngestController : ControllerBase
{
    private static readonly HashSet<string> AllowedTopics = new() { "golang", "python", "java", "dotnet" };
    private readonly IVectorStore _vectorStore;

    public TenantIngestController(IVectorStore vectorStore)
    {
        _vectorStore = vectorStore;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromBody] IngestRequest request)
    {
        if (!AllowedTopics.Contains(request.Topic))
            return BadRequest("Invalid topic");

        var collection = _vectorStore.GetCollection<string, ArticleRecord>("docs");
        var record = new ArticleRecord { Id = Guid.NewGuid().ToString(), Content = request.Topic };
        await collection.UpsertAsync(record);
        return Ok();
    }
}

public record IngestRequest(string Topic);

public class ArticleRecord
{
    public string Id { get; set; } = string.Empty;
    public string Content { get; set; } = string.Empty;
}
