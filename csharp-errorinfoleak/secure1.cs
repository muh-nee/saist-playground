using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

[ApiController]
[Route("[controller]")]
public class SecureUserController : ControllerBase
{
    private readonly IUserRepository _repo;
    private readonly ILogger<SecureUserController> _logger;

    public SecureUserController(IUserRepository repo, ILogger<SecureUserController> logger)
    {
        _repo = repo;
        _logger = logger;
    }

    [HttpGet("{id}")]
    public IActionResult GetUser(int id)
    {
        try
        {
            return Ok(_repo.GetUser(id));
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "GetUser failed for id {Id}", id);
            return StatusCode(500, new { error = "internal server error" });
        }
    }
}
