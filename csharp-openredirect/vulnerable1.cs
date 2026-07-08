using Microsoft.AspNetCore.Mvc;

namespace MyApp.Controllers;

// Vulnerable: user-controlled returnUrl parameter passed directly to Redirect
public class AccountController : Controller
{
    [HttpPost]
    public IActionResult Login(string username, string password, string returnUrl)
    {
        if (Authenticate(username, password))
        {
            // VULNERABLE: user-controlled redirect destination
            return Redirect(returnUrl);
        }
        return Unauthorized();
    }

    private bool Authenticate(string username, string password)
    {
        return username == "admin" && password == "secret";
    }
}
