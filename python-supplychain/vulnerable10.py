import tempfile

import requests
import safetensors.torch

MODEL_URL = "https://storage.example.com/models/llama_weights.safetensors"

def load_safetensors():
    resp = requests.get(MODEL_URL)
    with tempfile.NamedTemporaryFile(suffix=".safetensors", delete=False) as f:
        f.write(resp.content)
        tmp_path = f.name
    tensors = safetensors.torch.load_file(tmp_path)
    return tensors
