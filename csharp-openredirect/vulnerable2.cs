using Microsoft.AspNetCore.Mvc;

namespace MyApp.Controllers;

// Vulnerable: StartsWith check is insufficient — //evil.com bypasses it
public class RedirectController : Controller
{
    [HttpGet]
    public IActionResult Go(string url)
    {
        if (!string.IsNullOrEmpty(url) && url.StartsWith("/"))
        {
            // VULNERABLE: //evil.com/path starts with "/" but redirects to evil.com
            return Redirect(url);
        }
        return Redirect("/");
    }
}
