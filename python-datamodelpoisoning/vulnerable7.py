from flask import Flask, request, jsonify
from transformers import pipeline

app = Flask(__name__)

@app.route("/run-pipeline", methods=["POST"])
def run_pipeline():
    model_name = request.json.get("model_name")
    pipe = pipeline("text-classification", model=model_name)
    result = pipe(request.json.get("text"))
    return jsonify(result)
