from flask import Flask, request, jsonify, abort
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

system_prompt = "Internal assistant with access to payroll data."

@app.route("/admin/prompt")
def admin_prompt():
    if request.headers.get("X-Admin-Token") != "admin-secret":
        abort(403)
    return jsonify({"prompt": system_prompt})
