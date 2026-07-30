using Microsoft.SemanticKernel;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;

[ApiController]
[Route("[controller]")]
public class SummaryController : ControllerBase
{
    private readonly Kernel _kernel;
    private static readonly List<string> StoredSummaries = new();

    public SummaryController(Kernel kernel)
    {
        _kernel = kernel;
    }

    [HttpPost("store")]
    public async Task<IActionResult> Store([FromQuery] string topic)
    {
        var result = await _kernel.InvokePromptAsync("Summarize: " + topic);
        string summary = result.GetValue<string>()!;
        StoredSummaries.Add(summary);
        return Ok(new { status = "stored", count = StoredSummaries.Count });
    }
}
