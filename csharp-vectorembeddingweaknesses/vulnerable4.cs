using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.AI;

[ApiController]
[Route("api/[controller]")]
public class VectorIngestController : ControllerBase
{
    private readonly IVectorStoreRecordCollection<string, ArticleRecord> _collection;

    public VectorIngestController(IVectorStoreRecordCollection<string, ArticleRecord> collection)
    {
        _collection = collection;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromBody] IngestRequest request)
    {
        var record = new ArticleRecord { Id = Guid.NewGuid().ToString(), Content = request.Text };
        await _collection.UpsertAsync(record);
        return Ok();
    }
}

public record IngestRequest(string Text);

public class ArticleRecord
{
    public string Id { get; set; } = string.Empty;
    public string Content { get; set; } = string.Empty;
}
