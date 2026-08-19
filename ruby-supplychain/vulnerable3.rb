require "net/http"
require "tempfile"

MODEL_URL = "https://cdn.example.com/models/resnet50.pt"

def load_module
  response = Net::HTTP.get(URI(MODEL_URL))
  tmp = Tempfile.new(["model", ".pt"])
  tmp.write(response)
  tmp.rewind
  Torch::JIT.load(tmp.path)
end
