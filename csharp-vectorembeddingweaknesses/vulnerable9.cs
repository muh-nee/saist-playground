using Microsoft.AspNetCore.Mvc;
using Microsoft.SemanticKernel;

[ApiController]
[Route("api/[controller]")]
public class RouteIngestController : ControllerBase
{
    private readonly Kernel _kernel;

    public RouteIngestController(Kernel kernel)
    {
        _kernel = kernel;
    }

    [HttpPost("ingest/{text}")]
    public async Task<IActionResult> Ingest([FromRoute] string text)
    {
        await _kernel.Memory.SaveInformationAsync("docs", id: Guid.NewGuid().ToString(), text: text);
        return Ok();
    }
}
