import torch
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route("/load-checkpoint", methods=["POST"])
def load_checkpoint():
    model_path = request.json.get("checkpoint_path")
    model = torch.load(model_path)
    return jsonify({"status": "loaded"})

