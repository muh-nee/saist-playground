import google.generativeai as genai
from flask import Flask, request, jsonify

app = Flask(__name__)
genai.configure(api_key="key")
model = genai.GenerativeModel("gemini-1.5-pro")

@app.route("/explain", methods=["POST"])
def explain():
    topic = request.json["topic"]
    response = model.generate_content(
        topic,
        generation_config=genai.GenerationConfig(max_output_tokens=500),
    )
    return jsonify({"explanation": response.text})
