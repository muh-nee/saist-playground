import tempfile

import joblib
import requests

MODEL_URL = "https://cdn.example.com/models/sklearn_classifier.joblib"

def load_sklearn_model():
    resp = requests.get(MODEL_URL)
    with tempfile.NamedTemporaryFile(suffix=".joblib", delete=False) as f:
        f.write(resp.content)
        tmp_path = f.name
    model = joblib.load(tmp_path)
    return model
