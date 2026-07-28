from openai import OpenAI
from flask import Flask, request, jsonify

app = Flask(__name__)
client = OpenAI()

DISCLAIMER = "Note: This response is AI-generated and may contain inaccuracies. Please verify independently."

@app.route("/ask", methods=["POST"])
def ask():
    question = request.json["question"]
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=500,
        messages=[{"role": "user", "content": question}],
    )
    answer = response.choices[0].message.content
    return jsonify({"answer": answer, "disclaimer": DISCLAIMER})
