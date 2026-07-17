using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel.Memory;

[ApiController]
[Route("api/[controller]")]
public class KnowledgeController : ControllerBase
{
    private readonly ISemanticTextMemory _memory;

    public KnowledgeController(ISemanticTextMemory memory)
    {
        _memory = memory;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromBody] IngestRequest request)
    {
        await _memory.SaveInformationAsync("docs", id: Guid.NewGuid().ToString(), text: request.Text);
        return Ok();
    }
}

public record IngestRequest(string Text);
