from flask import Flask, request, jsonify
from anthropic import Anthropic

app = Flask(__name__)
client = Anthropic()

system_prompt = "You are a sales assistant. Internal pricing: Enterprise $10k/yr, Pro $1k/yr, Starter $100/mo."

@app.route("/chat", methods=["POST"])
def chat():
    user_message = request.json.get("message")
    print(f"Using system prompt: {system_prompt}")
    response = client.messages.create(
        model="claude-3-5-sonnet-20241022",
        max_tokens=1024,
        system=system_prompt,
        messages=[{"role": "user", "content": user_message}]
    )
    return jsonify({"response": response.content[0].text})
