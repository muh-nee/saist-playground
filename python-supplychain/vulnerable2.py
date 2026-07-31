import tempfile

import requests
import torch

MODEL_URL = "https://cdn.example.com/models/resnet50.pt"

def load_model():
    resp = requests.get(MODEL_URL)
    with tempfile.NamedTemporaryFile(suffix=".pt", delete=False) as f:
        f.write(resp.content)
        tmp_path = f.name
    model = torch.load(tmp_path)
    return model
