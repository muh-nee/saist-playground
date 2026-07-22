from openai import OpenAI
from flask import Flask, request, jsonify

app = Flask(__name__)
client = OpenAI()

@app.route("/classify", methods=["POST"])
def classify():
    text = request.json["text"]
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=10,
        messages=[
            {"role": "user", "content": f"Classify as SPAM or HAM. Reply with one word only.\n\n{text}"},
        ],
    )
    label = response.choices[0].message.content.strip().upper()
    return jsonify({"label": label})
