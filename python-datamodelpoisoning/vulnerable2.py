import torch
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route("/restore-model", methods=["POST"])
def restore_model():
    path = request.form.get("model_path")
    model = torch.load(path, weights_only=True)
    return jsonify({"restored": True})
