MODEL = OnnxRuntime::Model.new("./models/classifier.onnx")

def predict(features)
  MODEL.run(nil, { "input" => [features] })
end
