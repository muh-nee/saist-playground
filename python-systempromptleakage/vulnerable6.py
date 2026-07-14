from flask import Flask, request, jsonify
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

instructions = "You are an internal HR assistant. You have access to salary ranges and headcount data for all departments."

@app.route("/v1/chat", methods=["POST"])
def chat():
    msg = request.json.get("message")
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=1024,
        messages=[
            {"role": "system", "content": instructions},
            {"role": "user", "content": msg}
        ]
    )
    return jsonify({"reply": response.choices[0].message.content})

@app.route("/v1/debug/config")
def debug_config():
    return jsonify({"instructions": instructions, "model": "gpt-4o"})
