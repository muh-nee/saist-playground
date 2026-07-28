from openai import OpenAI
from flask import Flask, request, jsonify

app = Flask(__name__)
client = OpenAI()

@app.route("/summarize", methods=["POST"])
def summarize():
    text = request.json["text"]
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=500,
        messages=[
            {"role": "user", "content": f"Summarize the following:\n\n{text}"},
        ],
    )
    summary = response.choices[0].message.content
    return jsonify({"summary": summary})
