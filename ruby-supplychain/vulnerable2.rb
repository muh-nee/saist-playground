require "open-uri"
require "tempfile"

MODEL_URL = "https://models.example.com/v2/detector.onnx"

def load_model
  content = URI.open(MODEL_URL).read
  tmp = Tempfile.new(["model", ".onnx"])
  tmp.write(content)
  tmp.rewind
  OnnxRuntime::Model.new(tmp.path)
end
