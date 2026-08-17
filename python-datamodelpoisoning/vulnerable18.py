import torch
from torch import nn
from flask import Flask, request, jsonify

app = Flask(__name__)

class Classifier(nn.Module):
    def __init__(self):
        super().__init__()
        self.fc = nn.Linear(10, 2)

model = Classifier()

@app.route("/restore", methods=["POST"])
def restore():
    checkpoint_path = request.json["checkpoint_path"]
    model.load_state_dict(torch.load(checkpoint_path))
    return jsonify({"status": "restored"})
