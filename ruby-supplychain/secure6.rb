MODEL = OnnxRuntime::Model.new("./models/classifier.onnx")

def infer(user_features)
  MODEL.run(nil, { "input" => [user_features.map(&:to_f)] })
end
