using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.AI;
using System.Security.Claims;

[ApiController]
[Route("api/[controller]")]
public class TenantIngestController : ControllerBase
{
    private readonly IVectorStore _vectorStore;

    public TenantIngestController(IVectorStore vectorStore)
    {
        _vectorStore = vectorStore;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromBody] IngestRequest request)
    {
        var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
        var collection = _vectorStore.GetCollection<string, ArticleRecord>($"user_{userId}");
        var record = new ArticleRecord { Id = Guid.NewGuid().ToString(), Content = request.Text };
        await collection.UpsertAsync(record);
        return Ok();
    }
}

public record IngestRequest(string Text);

public class ArticleRecord
{
    public string Id { get; set; } = string.Empty;
    public string Content { get; set; } = string.Empty;
}
