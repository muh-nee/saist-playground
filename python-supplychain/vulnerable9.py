import tempfile

import onnxruntime as ort
import requests

MODEL_URL = "https://registry.example.com/models/embedding.onnx"

def load_onnx_model():
    resp = requests.get(MODEL_URL)
    with tempfile.NamedTemporaryFile(suffix=".onnx", delete=False) as f:
        f.write(resp.content)
        tmp_path = f.name
    session = ort.InferenceSession(tmp_path)
    return session
