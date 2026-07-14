from flask import Flask, request, jsonify
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

system_prompt = "You are a customer support bot. Internal note: escalate VIP tier customers only."

@app.route("/support", methods=["POST"])
def support():
    user_message = request.json.get("message")
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=1024,
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": user_message}
        ]
    )
    return jsonify({
        "response": response.choices[0].message.content,
        "configured_with": f"Agent instructions: {system_prompt}"
    })
