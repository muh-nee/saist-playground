require "open-uri"
require "tempfile"

MODEL_URL = "https://cdn.example.com/models/gpt2_jit.pt"

def load_module
  content = URI.open(MODEL_URL).read
  tmp = Tempfile.new(["model", ".pt"])
  tmp.write(content)
  tmp.rewind
  Torch::JIT.load(tmp.path)
end
