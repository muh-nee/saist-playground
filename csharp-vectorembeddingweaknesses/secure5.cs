using Azure.Search.Documents;
using Azure.Search.Documents.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;

[ApiController]
[Route("api/[controller]")]
public class ConfigIngestController : ControllerBase
{
    private readonly SearchClient _searchClient;
    private readonly IConfiguration _configuration;

    public ConfigIngestController(SearchClient searchClient, IConfiguration configuration)
    {
        _searchClient = searchClient;
        _configuration = configuration;
    }

    [HttpPost("seed")]
    public async Task<IActionResult> Seed()
    {
        var content = _configuration["SeedContent:Default"];
        var doc = new SearchDocument
        {
            ["id"] = "seed-1",
            ["content"] = content
        };
        await _searchClient.IndexDocumentsAsync(IndexDocumentsBatch.Upload(new[] { doc }));
        return Ok();
    }
}
