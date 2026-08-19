MODEL_PATH = ENV.fetch("MODEL_PATH")
MODEL = OnnxRuntime::Model.new(MODEL_PATH)

def infer(features)
  MODEL.run(nil, { "input" => [features] })
end
