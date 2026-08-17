import requests as http_requests
from flask import Flask, request, jsonify
from transformers import AutoModelForCausalLM, Trainer, TrainingArguments
from datasets import Dataset

app = Flask(__name__)
model = AutoModelForCausalLM.from_pretrained("gpt2")

@app.route("/train-from-url", methods=["POST"])
def train_from_url():
    dataset_url = request.json["dataset_url"]
    response = http_requests.get(dataset_url)
    samples = response.json()
    dataset = Dataset.from_list(samples)
    Trainer(model=model, args=TrainingArguments("./out"), train_dataset=dataset).train()
    return jsonify({"status": "trained"})
