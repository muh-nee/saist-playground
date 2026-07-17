using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel.Memory;

[ApiController]
[Route("api/[controller]")]
[Authorize(Roles = "Admin")]
public class AdminKnowledgeController : ControllerBase
{
    private readonly ISemanticTextMemory _memory;

    public AdminKnowledgeController(ISemanticTextMemory memory)
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
