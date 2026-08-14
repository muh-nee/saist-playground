from flask import Flask, jsonify
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

tools = [
    {"type": "function", "function": {
        "name": "get_user_records",
        "description": "Retrieves all internal user records. Admin use only.",
        "parameters": {"type": "object", "properties": {"user_id": {"type": "string"}}},
    }}
]

@app.route("/chat", methods=["POST"])
def chat():
    client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": "hello"}],
        tools=tools,
    )
    return jsonify({"reply": "ok"})

@app.route("/debug/tools")
def debug_tools():
    return jsonify({"tools": tools})
