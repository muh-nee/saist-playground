using Microsoft.AspNetCore.Mvc;
using Microsoft.KernelMemory;

[ApiController]
[Route("api/[controller]")]
public class DocumentIngestController : ControllerBase
{
    private readonly IKernelMemory _memory;

    public DocumentIngestController(IKernelMemory memory)
    {
        _memory = memory;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromBody] IngestRequest request)
    {
        var document = new Document(request.DocumentId).AddStream(
            request.FileName,
            new System.IO.MemoryStream(System.Text.Encoding.UTF8.GetBytes(request.Content))
        );
        await _memory.ImportDocumentAsync(document);
        return Ok();
    }
}

public record IngestRequest(string DocumentId, string FileName, string Content);
