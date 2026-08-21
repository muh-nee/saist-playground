require "httparty"
require "stringio"

MODEL_URL = "https://cdn.example.com/models/classifier.onnx"

def load_model
  response = HTTParty.get(MODEL_URL)
  OnnxRuntime::Model.new(StringIO.new(response.body))
end

