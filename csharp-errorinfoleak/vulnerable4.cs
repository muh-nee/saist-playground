using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("[controller]")]
public class DataController : ControllerBase
{
    [HttpGet]
    public IActionResult GetData()
    {
        try
        {
            var data = FetchData();
            return Ok(data);
        }
        catch (Exception ex)
        {
            return Content(ex.ToString(), "text/plain");
        }
    }

    private object FetchData() => new { value = 42 };
}
