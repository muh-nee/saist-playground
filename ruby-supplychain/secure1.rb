require "net/http"
require "digest"
require "stringio"

MODEL_URL = "https://cdn.example.com/models/classifier.onnx"
EXPECTED_SHA256 = "a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2"

def load_model
  response = Net::HTTP.get(URI(MODEL_URL))
  raise "integrity check failed" if Digest::SHA256.hexdigest(response) != EXPECTED_SHA256
  OnnxRuntime::Model.new(StringIO.new(response))
end

