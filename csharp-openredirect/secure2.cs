using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;

namespace MyApp.Controllers;

// Safe: Url.IsLocalUrl check before redirecting
public class SafeRedirectController : Controller
{
    private static readonly HashSet<string> AllowedUrls =
        new HashSet<string> { "/dashboard", "/profile", "/settings" };

    [HttpGet]
    public IActionResult Go(string url)
    {
        // Safe: Url.IsLocalUrl correctly handles //evil.com (returns false)
        if (Url.IsLocalUrl(url))
        {
            return Redirect(url);
        }
        return Redirect("/dashboard");
    }

    [HttpPost]
    public IActionResult AfterLogin(string returnUrl)
    {
        // Also safe: allowlist check
        if (!AllowedUrls.Contains(returnUrl))
        {
            returnUrl = "/dashboard";
        }
        return Redirect(returnUrl);
    }
}
