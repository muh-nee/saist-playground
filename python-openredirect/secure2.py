from flask import Flask, redirect, url_for, request

app = Flask(__name__)

ALLOWED_REDIRECTS = {"/dashboard", "/profile", "/settings", "/home"}


# Safe: allowlist of permitted redirect destinations
@app.route("/login", methods=["POST"])
def login():
    username = request.form.get("username")
    password = request.form.get("password")

    if authenticate(username, password):
        next_url = request.args.get("next", "/dashboard")
        # Only redirect to explicitly allowed destinations
        if next_url not in ALLOWED_REDIRECTS:
            next_url = "/dashboard"
        return redirect(next_url)
    return "Unauthorized", 401


@app.route("/dashboard")
def dashboard():
    return "Welcome to dashboard"


def authenticate(username, password):
    return username == "admin" and password == "secret"


if __name__ == "__main__":
    app.run()
