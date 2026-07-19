from flask import Flask, request, jsonify
from transformers import pipeline
from datasets import Dataset

app = Flask(__name__)
pipe = pipeline("text-classification", model="distilbert-base-uncased-finetuned-sst-2-english")

@app.route("/classify-batch", methods=["POST"])
def classify_batch():
    texts = request.json.get("texts")
    dataset = Dataset.from_list([{"text": t} for t in texts])
    results = pipe(dataset["text"])
    return jsonify(results)
