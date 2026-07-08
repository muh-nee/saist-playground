from flask import Flask, request, jsonify
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

system_prompt = "You are a financial analysis assistant. Internal: use ACME Corp margin data and confidential pricing."

@app.route("/analyze", methods=["POST"])
def analyze():
    try:
        data = request.json
        response = client.chat.completions.create(
            model="gpt-4o",
            messages=[
                {"role": "system", "content": system_prompt},
                {"role": "user", "content": data["query"]}
            ]
        )
        return jsonify({"result": response.choices[0].message.content})
    except Exception as e:
        return jsonify({"error": str(e), "context": system_prompt}), 500
