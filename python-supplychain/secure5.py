import hashlib
import tempfile

import requests
import torch

MODEL_URL = "https://cdn.example.com/models/resnet50.pt"
EXPECTED_SHA256 = "deadbeef1234567890abcdef1234567890abcdef1234567890abcdef12345678"

def load_model():
    resp = requests.get(MODEL_URL)
    if hashlib.sha256(resp.content).hexdigest() != EXPECTED_SHA256:
        raise ValueError("integrity check failed")
    with tempfile.NamedTemporaryFile(suffix=".pt", delete=False) as f:
        f.write(resp.content)
        tmp_path = f.name
    model = torch.load(tmp_path)
    return model
