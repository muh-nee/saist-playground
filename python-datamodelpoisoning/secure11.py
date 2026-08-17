from flask import Flask, request, jsonify
from transformers import AutoTokenizer

app = Flask(__name__)

TOKENIZER_NAME = "gpt2"
tokenizer = AutoTokenizer.from_pretrained(TOKENIZER_NAME)

@app.route("/tokenize", methods=["POST"])
def tokenize():
    text = request.json["text"]
    tokens = tokenizer(text, return_tensors="pt")
    return jsonify({"input_ids": tokens["input_ids"].tolist()})
