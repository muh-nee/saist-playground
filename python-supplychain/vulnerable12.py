import torch

def load_model():
    model = torch.hub.load("pytorch/vision", "resnet50", pretrained=True)
    return model
