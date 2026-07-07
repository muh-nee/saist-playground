using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class IndexController : ControllerBase
{
    private static readonly int[] Data = new int[10000];

    [HttpGet]
    public IActionResult Read([FromQuery] string indexStr)
    {
        int index = int.Parse(indexStr);
        int offset = 1000;
        return Ok(Data[offset + index]); // offset + index may overflow to negative
    }
}
