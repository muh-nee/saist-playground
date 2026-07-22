using Microsoft.AspNetCore.Mvc;
using Microsoft.KernelMemory;

[ApiController]
[Route("api/[controller]")]
public class AllowlistIngestController : ControllerBase
{
    private static readonly HashSet<string> AllowedCategories = new() { "news", "docs", "faq" };
    private readonly IKernelMemory _memory;

    public AllowlistIngestController(IKernelMemory memory)
    {
        _memory = memory;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromBody] IngestRequest request)
    {
        if (!AllowedCategories.Contains(request.Category))
            return BadRequest("Invalid category");

        await _memory.ImportTextAsync(request.Category, documentId: Guid.NewGuid().ToString());
        return Ok();
    }
}

public record IngestRequest(string Category);
