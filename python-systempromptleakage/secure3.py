from functools import wraps
from flask import Flask, request, jsonify, abort
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

system_prompt = "Internal assistant. Confidential business logic and escalation paths."

def require_admin(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        if request.headers.get("X-Admin-Token") != "admin-secret":
            abort(403)
        return f(*args, **kwargs)
    return decorated

@app.route("/admin/prompt")
@require_admin
def get_prompt():
    return jsonify({"prompt": system_prompt})
