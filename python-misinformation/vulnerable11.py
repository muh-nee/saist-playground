from anthropic import Anthropic
from flask import Flask, request, jsonify

app = Flask(__name__)
client = Anthropic()

@app.route("/medical-info", methods=["POST"])
def medical_info():
    condition = request.json["condition"]
    message = client.messages.create(
        model="claude-opus-4-5",
        max_tokens=500,
        messages=[{"role": "user", "content": f"What should I know about {condition}?"}],
    )
    info = message.content[0].text
    return jsonify({"information": info})
