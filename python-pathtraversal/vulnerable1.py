from flask import Flask, request, send_file
import os

app = Flask(__name__)
BASE_DIR = "/var/data"


@app.route("/file")
def get_file():
    filename = request.args.get("name", "")
    path = os.path.join(BASE_DIR, filename)
    with open(path, "r") as f:
        return f.read()


@app.route("/download")
def download():
    name = request.headers.get("X-Filename", "")
    return send_file(os.path.join("/uploads", name))
