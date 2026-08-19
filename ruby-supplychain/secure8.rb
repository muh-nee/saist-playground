require "down"
require "digest"

MODEL_URL = "https://cdn.example.com/models/classifier.onnx"
EXPECTED_SHA256 = "f4a5b6c7d8e9f0a1b2c3d4e5f6..."

def load_model
  file = Down.download(MODEL_URL)
  raise "integrity check failed" if Digest::SHA256.file(file.path).hexdigest != EXPECTED_SHA256
  OnnxRuntime::Model.new(file.path)
end
