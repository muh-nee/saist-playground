import pickle
import requests

MODEL_URL = "https://models.example.com/classifier.pkl"

def load_model():
    resp = requests.get(MODEL_URL)
    model = pickle.loads(resp.content)
    return model

