require "rest-client"
require "tempfile"

MODEL_URL = "https://models.example.com/resnet50.pt"

def load_module
  response = RestClient.get(MODEL_URL)
  tmp = Tempfile.new(["model", ".pt"])
  tmp.write(response.body)
  tmp.rewind
  Torch::JIT.load(tmp.path)
end
