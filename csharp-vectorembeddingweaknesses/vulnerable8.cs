using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel.Memory;

[ApiController]
[Route("api/[controller]")]
public class QueryIngestController : ControllerBase
{
    private readonly ISemanticTextMemory _memory;

    public QueryIngestController(ISemanticTextMemory memory)
    {
        _memory = memory;
    }

    [HttpPost("ingest")]
    public async Task<IActionResult> Ingest([FromQuery] string text)
    {
        await _memory.SaveInformationAsync("docs", id: Guid.NewGuid().ToString(), text: text);
        return Ok();
    }
}
