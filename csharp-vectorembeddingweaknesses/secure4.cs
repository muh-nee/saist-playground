using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.Memory;

[ApiController]
[Route("api/[controller]")]
public class LlmMemoryController : ControllerBase
{
    private readonly Kernel _kernel;
    private readonly ISemanticTextMemory _memory;

    public LlmMemoryController(Kernel kernel, ISemanticTextMemory memory)
    {
        _kernel = kernel;
        _memory = memory;
    }

    [HttpPost("ask")]
    public async Task<IActionResult> Ask([FromBody] AskRequest request)
    {
        var result = await _kernel.InvokePromptAsync(request.Question);
        await _memory.SaveInformationAsync("answers", id: Guid.NewGuid().ToString(), text: result.ToString());
        return Ok(new { answer = result.ToString(), disclaimer = "AI-generated content. Verify independently." });
    }
}

public record AskRequest(string Question);
