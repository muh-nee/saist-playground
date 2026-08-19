require "open-uri"

MODEL_URL = "https://cdn.example.com/models/model.marshal"

def load_model
  response = URI.open(MODEL_URL).read
  Marshal.load(response)
end
