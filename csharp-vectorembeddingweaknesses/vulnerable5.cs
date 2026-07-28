using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.AI;

[ApiController]
[Route("api/[controller]")]
public class FormIngestController : ControllerBase
{
    private readonly IVectorStore _vectorStore;

    public FormIngestController(IVectorStore vectorStore)
    {
        _vectorStore = vectorStore;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromForm] string text)
    {
        var collection = _vectorStore.GetCollection<string, ArticleRecord>("docs");
        var record = new ArticleRecord { Id = Guid.NewGuid().ToString(), Content = text };
        await collection.UpsertAsync(record);
        return Ok();
    }
}

public class ArticleRecord
{
    public string Id { get; set; } = string.Empty;
    public string Content { get; set; } = string.Empty;
}
