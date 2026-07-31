import tempfile
import urllib.request

import torch

MODEL_URL = "https://storage.example.com/models/gpt2_weights.pt"

def load_weights():
    tmp_path = tempfile.mktemp(suffix=".pt")
    urllib.request.urlretrieve(MODEL_URL, tmp_path)
    model = torch.load(tmp_path)
    return model
