from flask import Flask, request, jsonify
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

system_prompt = "You are a code review assistant. Review the provided code and suggest improvements."

@app.route("/review", methods=["POST"])
def review():
    code = request.json.get("code")
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=1024,
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": f"Review this code:\n{code}"}
        ]
    )
    llm_output = response.choices[0].message.content
    return jsonify({"review": llm_output})
