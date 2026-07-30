using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.Memory;
using Microsoft.AspNetCore.Mvc;
using System.Text;

[ApiController]
[Route("[controller]")]
public class RagController : ControllerBase
{
    private readonly Kernel _kernel;
    private readonly ISemanticTextMemory _memory;

    public RagController(Kernel kernel, ISemanticTextMemory memory)
    {
        _kernel = kernel;
        _memory = memory;
    }

    [HttpPost("ask")]
    public async Task<IActionResult> Ask([FromQuery] string question)
    {
        var memories = _memory.SearchAsync("docs", question, limit: 3);
        var sources = new List<string>();
        var contextBuilder = new StringBuilder();
        await foreach (var m in memories)
        {
            sources.Add(m.Metadata.Id);
            contextBuilder.AppendLine(m.Metadata.Text);
        }
        string prompt = $"Context:\n{contextBuilder}\n\nQuestion: {question}";
        var result = await _kernel.InvokePromptAsync(prompt);
        string answer = result.GetValue<string>()!;
        return Ok(new { answer, sources });
    }
}
