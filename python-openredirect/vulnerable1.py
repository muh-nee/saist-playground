from flask import Flask, redirect, request

app = Flask(__name__)


# Vulnerable: user-controlled query parameter passed directly to redirect
@app.route("/login", methods=["POST"])
def login():
    username = request.form.get("username")
    password = request.form.get("password")

    if authenticate(username, password):
        next_url = request.args.get("next", "/")
        # VULNERABLE: user-controlled redirect destination
        return redirect(next_url)
    return "Unauthorized", 401


def authenticate(username, password):
    return username == "admin" and password == "secret"


if __name__ == "__main__":
    app.run()
