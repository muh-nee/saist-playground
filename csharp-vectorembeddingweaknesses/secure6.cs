using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel.Memory;

[ApiController]
[Route("api/[controller]")]
public class EphemeralMemoryController : ControllerBase
{
    [HttpPost("search")]
    public async Task<IActionResult> Search([FromBody] SearchRequest request)
    {
        var store = new VolatileMemoryStore();
        var memory = new SemanticTextMemory(store, null!);
        await memory.SaveInformationAsync("session", id: "doc1", text: "Go programming guide");
        var results = memory.SearchAsync("session", request.Query, limit: 3);
        return Ok();
    }
}

public record SearchRequest(string Query);
