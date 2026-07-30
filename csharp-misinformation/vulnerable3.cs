using Microsoft.SemanticKernel;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class AskController : ControllerBase
{
    private readonly Kernel _kernel;

    public AskController(Kernel kernel)
    {
        _kernel = kernel;
    }

    [HttpPost("ask")]
    public async Task<IActionResult> Ask([FromQuery] string question)
    {
        var result = await _kernel.InvokePromptAsync(question);
        string answer = result.GetValue<string>()!;
        return Ok(new { answer });
    }
}
