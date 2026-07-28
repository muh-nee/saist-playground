import torch
from flask import Flask, request, jsonify

app = Flask(__name__)
APPROVED_CHECKPOINTS = {"v1", "v2", "latest"}

@app.route("/load-checkpoint", methods=["POST"])
def load_checkpoint():
    checkpoint_name = request.json.get("checkpoint_name")
    if checkpoint_name not in APPROVED_CHECKPOINTS:
        return jsonify({"error": "unauthorized checkpoint"}), 403
    model = torch.load(f"./checkpoints/{checkpoint_name}.pt")
    return jsonify({"status": "loaded"})