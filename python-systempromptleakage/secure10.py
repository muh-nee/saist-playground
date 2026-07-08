from flask import Flask, request, jsonify
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

system_prompt = "You are a data analysis assistant. Summarize the provided data."

@app.route("/analyze", methods=["POST"])
def analyze():
    user_data = request.json.get("data")
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": f"Analyze this data: {user_data}"}
        ]
    )
    return jsonify({"analysis": response.choices[0].message.content})
