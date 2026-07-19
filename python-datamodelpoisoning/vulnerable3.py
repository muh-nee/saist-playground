import torch
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route("/load-jit", methods=["POST"])
def load_jit_model():
    model_path = request.json.get("model_path")
    module = torch.jit.load(model_path)
    return jsonify({"loaded": True})
