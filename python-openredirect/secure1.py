from urllib.parse import urlparse

from flask import Flask, redirect, request

app = Flask(__name__)


# Safe: parse URL and verify no scheme/netloc before redirecting
@app.route("/login", methods=["POST"])
def login():
    username = request.form.get("username")
    password = request.form.get("password")

    if authenticate(username, password):
        next_url = request.args.get("next", "/")
        parsed = urlparse(next_url)
        # Reject URLs with a scheme or netloc (host) — those are absolute or scheme-relative
        if parsed.scheme or parsed.netloc:
            next_url = "/dashboard"
        return redirect(next_url)
    return "Unauthorized", 401


def authenticate(username, password):
    return username == "admin" and password == "secret"


if __name__ == "__main__":
    app.run()
