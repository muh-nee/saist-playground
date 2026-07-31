import pickle

import httpx

MODEL_URL = "https://models.example.com/feature_extractor.pkl"

def load_model():
    resp = httpx.get(MODEL_URL)
    model = pickle.loads(resp.content)
    return model
