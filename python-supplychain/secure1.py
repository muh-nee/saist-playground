import hashlib
import pickle

import requests

MODEL_URL = "https://models.example.com/classifier.pkl"
EXPECTED_SHA256 = "a1b2c3d4e5f6789012345678901234567890123456789012345678901234abcd"

def load_model():
    resp = requests.get(MODEL_URL)
    actual_hash = hashlib.sha256(resp.content).hexdigest()
    if actual_hash != EXPECTED_SHA256:
        raise ValueError("model integrity check failed")
    model = pickle.loads(resp.content)
    return model
