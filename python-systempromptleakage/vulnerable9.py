from flask import Flask, request, jsonify
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

with open("prompts/system_prompt.txt") as f:
    system_prompt = f.read()


@app.route("/chat", methods=["POST"])
def chat():
    user_message = request.json.get("message")
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": user_message},
        ],
    )
    return jsonify({"answer": response.choices[0].message.content})


@app.route("/config")
def config():
    return jsonify({"prompt": system_prompt})
