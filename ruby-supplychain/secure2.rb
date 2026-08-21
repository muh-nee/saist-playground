require "open-uri"
require "digest"
require "tempfile"

MODEL_URL = "https://cdn.example.com/models/resnet50.pt"
EXPECTED_SHA256 = "b94d27b9934d3e08a52e52d7da7dabfac484efe..."

def load_module
  content = URI.open(MODEL_URL).read
  tmp = Tempfile.new(["model", ".pt"])
  tmp.write(content)
  tmp.flush
  raise "integrity check failed" if Digest::SHA256.file(tmp.path).hexdigest != EXPECTED_SHA256
  Torch::JIT.load(tmp.path)
end

