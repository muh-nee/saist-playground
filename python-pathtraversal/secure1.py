from flask import Flask, request, abort
import os

app = Flask(__name__)
BASE_DIR = os.path.realpath("/var/data")


@app.route("/file")
def get_file():
    filename = request.args.get("name", "")
    target = os.path.realpath(os.path.join(BASE_DIR, filename))
    if not target.startswith(BASE_DIR + os.sep):
        abort(403)
    with open(target, "r") as f:
        return f.read()
