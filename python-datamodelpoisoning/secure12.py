import hashlib
import requests as http_requests
from flask import Flask, jsonify
from transformers import AutoModelForCausalLM, Trainer, TrainingArguments
from datasets import Dataset

app = Flask(__name__)
model = AutoModelForCausalLM.from_pretrained("gpt2")

TRUSTED_URL = "https://internal.example.com/datasets/approved.json"
EXPECTED_SHA256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"

@app.route("/train-verified", methods=["POST"])
def train_verified():
    response = http_requests.get(TRUSTED_URL)
    content = response.content
    if hashlib.sha256(content).hexdigest() != EXPECTED_SHA256:
        return jsonify({"error": "integrity check failed"}), 403
    dataset = Dataset.from_list(response.json())
    Trainer(model=model, args=TrainingArguments("./out"), train_dataset=dataset).train()
    return jsonify({"status": "trained"})
