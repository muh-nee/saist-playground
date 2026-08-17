from flask import Flask, request, jsonify
from transformers import AutoTokenizer

app = Flask(__name__)

@app.route("/load-tokenizer", methods=["POST"])
def load_tokenizer():
    tokenizer_name = request.json["tokenizer_name"]
    tokenizer = AutoTokenizer.from_pretrained(tokenizer_name)
    return jsonify({"vocab_size": tokenizer.vocab_size})
