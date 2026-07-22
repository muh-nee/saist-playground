using Azure.Search.Documents;
using Azure.Search.Documents.Models;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("api/[controller]")]
public class SearchIngestController : ControllerBase
{
    private readonly SearchClient _searchClient;

    public SearchIngestController(SearchClient searchClient)
    {
        _searchClient = searchClient;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromBody] IngestRequest request)
    {
        var doc = new SearchDocument
        {
            ["id"] = Guid.NewGuid().ToString(),
            ["content"] = request.Text
        };
        await _searchClient.IndexDocumentsAsync(IndexDocumentsBatch.Upload(new[] { doc }));
        return Ok();
    }
}

public record IngestRequest(string Text);
