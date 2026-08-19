require "tempfile"

WEIGHTS_URL = "https://cdn.example.com/models/bert_weights.pt"

def load_weights
  conn = Faraday.new
  response = conn.get(WEIGHTS_URL)
  tmp = Tempfile.new(["weights", ".pt"])
  tmp.write(response.body)
  tmp.rewind
  Torch.load(tmp.path)
end
