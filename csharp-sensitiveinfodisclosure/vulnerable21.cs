using Microsoft.Extensions.Logging;

public class AuthService
{
    private readonly ILogger<AuthService> _logger;

    public AuthService(ILogger<AuthService> logger)
    {
        _logger = logger;
    }

    public bool Authenticate(string username, string password)
    {
        _logger.LogInformation($"Authenticating user {username} with password {password}");
        return true;
    }
}
