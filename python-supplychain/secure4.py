import torch

MODEL_PATH = "./models/classifier_v2.pt"

def load_model():
    model = torch.load(MODEL_PATH)
    return model
