require "down"
require "tempfile"

MODEL_URL = "https://cdn.example.com/models/bert_weights.pt"

def load_weights
  file = Down.download(MODEL_URL)
  Torch.load(file.path)
end
