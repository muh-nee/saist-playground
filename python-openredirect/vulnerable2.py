from django.http import HttpResponseRedirect
from django.views import View


class LoginView(View):
    # Vulnerable: Django redirect with user-controlled GET parameter
    def post(self, request):
        username = request.POST.get("username")
        password = request.POST.get("password")

        if self.authenticate(username, password):
            next_url = request.GET.get("next", "/")
            # VULNERABLE: startswith check is insufficient — //evil.com bypasses it
            if next_url.startswith("/"):
                return HttpResponseRedirect(next_url)
            return HttpResponseRedirect("/")
        return HttpResponseRedirect("/login?error=1")

    def authenticate(self, username, password):
        return username == "admin" and password == "secret"
