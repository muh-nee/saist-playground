MODEL_PATH = Rails.root.join("models", "resnet50.pt").to_s
MODEL = Torch::JIT.load(MODEL_PATH)

def classify(image_tensor)
  MODEL.forward(image_tensor)
end
