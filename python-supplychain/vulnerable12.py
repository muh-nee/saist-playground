import torch
from flask import Flask, jsonify

app = Flask(__name__)

@app.route("/load", methods=["POST"])
def load():
    model = torch.hub.load("pytorch/vision:v0.10.0", "resnet50", pretrained=True)
    return jsonify({"status": "loaded"})
