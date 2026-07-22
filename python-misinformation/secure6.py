from openai import OpenAI
from flask import Flask, request, jsonify

app = Flask(__name__)
client = OpenAI()

VALID_LABELS = {"SPAM", "HAM"}

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
    if label not in VALID_LABELS:
        label = "UNKNOWN"
    return jsonify({"label": label})
