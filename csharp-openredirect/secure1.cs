using Microsoft.AspNetCore.Mvc;

namespace MyApp.Controllers;

// Safe: using LocalRedirect which throws InvalidOperationException for non-local URLs
public class SafeAccountController : Controller
{
    [HttpPost]
    public IActionResult Login(string username, string password, string returnUrl)
    {
        if (Authenticate(username, password))
        {
            // Safe: LocalRedirect throws if URL is not local/relative
            return LocalRedirect(returnUrl ?? "/dashboard");
        }
        return Unauthorized();
    }

    private bool Authenticate(string username, string password)
    {
        return username == "admin" && password == "secret";
    }
}
