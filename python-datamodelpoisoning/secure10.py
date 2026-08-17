import torch
from torch import nn

class Classifier(nn.Module):
    def __init__(self):
        super().__init__()
        self.fc = nn.Linear(10, 2)

model = Classifier()
model.load_state_dict(torch.load("./checkpoints/prod_v3.pt", weights_only=True))
