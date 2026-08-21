require "net/http"
require "stringio"

MODEL_URL = "https://cdn.example.com/models/classifier.onnx"

def load_model
  response = Net::HTTP.get(URI(MODEL_URL))
  OnnxRuntime::Model.new(StringIO.new(response))
end

