from openai import OpenAI
from flask import Flask, request, jsonify

app = Flask(__name__)
client = OpenAI()

def requires_human_review(answer: str) -> bool:
    keywords = ["diagnosis", "treatment", "legal", "invest", "medication"]
    return any(kw in answer.lower() for kw in keywords)

@app.route("/advise", methods=["POST"])
def advise():
    question = request.json["question"]
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=500,
        messages=[{"role": "user", "content": question}],
    )
    answer = response.choices[0].message.content
    if requires_human_review(answer):
        return jsonify({"status": "pending_review", "message": "This response requires human review before delivery."})
    return jsonify({"answer": answer, "disclaimer": "AI-generated. Verify before use."})
